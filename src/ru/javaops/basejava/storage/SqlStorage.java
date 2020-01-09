package ru.javaops.basejava.storage;

import org.intellij.lang.annotations.Language;
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
        @Language("SQL") String strSql = "DELETE FROM resume";
        sqlHelper.execute(strSql, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        @Language("SQL") String strSql = "" +
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
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resume.setContact(type, value);
                } while (rs.next());
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        @Language("SQL") final String strSql = "" +
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
        @Language("SQL") final String strSql = "" +
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
        @Language("SQL") String strSql = "" +
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
        Map<String, Resume> map = new LinkedHashMap<>();
        @Language("SQL") String strSql = "" +
                "    SELECT * FROM resume" +
                " LEFT JOIN contact " +
                "        ON resume.uuid = contact.resume_uuid " +
                "  ORDER BY resume.full_name, resume.uuid";

        return sqlHelper.execute(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            boolean notEndQuery = false;
            if (rs.next()) {
                notEndQuery = true;
            }
            while (notEndQuery) {
                String uuid = rs.getString("uuid").trim();
                String full_name = rs.getString("full_name");
                Resume resume = new Resume(uuid, full_name);
                map.put(uuid, resume);
                while (map.containsKey(rs.getString("uuid").trim())) {
//                while (uuid.equals(rs.getString("uuid").trim())) {
                    if (Objects.nonNull(rs.getString("type"))) {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
//                        map.get(uuid).setContact(type, value);
                        resume.setContact(type, value);
                    }
                    if (!rs.next()) {
                        notEndQuery = false;
                        break;
                    }
                }
//                map.put(uuid, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        @Language("SQL") String strSql = "" +
                "SELECT COUNT(*)" +
                "  FROM resume";
        return sqlHelper.execute(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }


    private void deleteContact(Connection conn, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        @Language("SQL") String strSql = "" +
                "DELETE FROM contact" +
                " WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(strSql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void saveContact(Connection conn, Resume resume) throws SQLException {
        @Language("SQL") final String strSql = "" +
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
}
