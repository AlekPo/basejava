package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String strSql = "DELETE FROM resume";
        sqlHelper.execute(strSql, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String strSql = "" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN contact c " +
                "     ON r.uuid = c.resume_uuid " +
                "  WHERE r.uuid =?";
        return sqlHelper.execute(strSql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            if (Objects.nonNull(rs.getString("type"))) {
                do {
                    addContact(rs, resume);
                } while (rs.next());
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        String strSql = "" +
                "UPDATE resume SET full_name = ?" +
                " WHERE uuid = ?";
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(strSql)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(conn, resume);
            saveContact(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String strSql = "" +
                "INSERT INTO resume (uuid, full_name)" +
                " VALUES (?,?)";
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(strSql)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            saveContact(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        String strSql = "" +
                "DELETE FROM resume" +
                " WHERE resume.uuid = ?";
        sqlHelper.execute(strSql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String strSql = "" +
                "    SELECT * FROM resume" +
                " LEFT JOIN contact " +
                "        ON resume.uuid = contact.resume_uuid " +
                "  ORDER BY resume.full_name, resume.uuid";
        Map<String, Resume> map = new LinkedHashMap<>();
        return sqlHelper.execute(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String full_name = rs.getString("full_name");
                Resume resume = map.computeIfAbsent(uuid, (v -> new Resume(uuid, full_name)));
                if (Objects.nonNull(rs.getString("type"))) {
                    addContact(rs, resume);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        String strSql = "" +
                "SELECT COUNT(*)" +
                "  FROM resume";
        return sqlHelper.execute(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }


    private void deleteContact(Connection conn, Resume resume) throws SQLException {
        String strSql = "" +
                "DELETE FROM contact" +
                " WHERE resume_uuid = ?";
        String uuid = resume.getUuid();
        try (PreparedStatement ps = conn.prepareStatement(strSql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void saveContact(Connection conn, Resume resume) throws SQLException {
        String strSql = "" +
                "INSERT INTO contact (resume_uuid, type, value)" +
                " VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(strSql)) {
            String uuid = resume.getUuid();
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        ContactType type = ContactType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        resume.setContact(type, value);
    }
}
