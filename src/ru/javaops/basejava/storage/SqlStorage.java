package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.*;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? " + e.toString() + e.getMessage());
        }

        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String strSql = "DELETE FROM resume";
        sqlHelper.execute(strSql);
    }

    @Override
    public Resume get(String uuid) {
        String strSql1 = "" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN contact c " +
                "     ON r.uuid = c.resume_uuid " +
                "  WHERE r.uuid =?";
        String strSql2 = "" +
                " SELECT * FROM section s " +
                "  WHERE s.resume_uuid =?";
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement(strSql1)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, resume);
                } while (rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(strSql2)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
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
            deleteContacts(conn, resume);
            insertContacts(conn, resume);
            deleteSections(conn, resume);
            insertSections(conn, resume);
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
            insertContacts(conn, resume);
            insertSections(conn, resume);
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
        String strSql1 = "" +
                "    SELECT * FROM resume" +
                "  ORDER BY full_name, uuid";
        String strSql2 = "" +
                "    SELECT * FROM contact" +
                "  ORDER BY resume_uuid";
        String strSql3 = "" +
                "    SELECT * FROM section" +
                "  ORDER BY resume_uuid";
        Map<String, Resume> map = new LinkedHashMap<>();

        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(strSql1)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String full_name = rs.getString("full_name");
                    map.put(uuid, new Resume(uuid, full_name));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(strSql2)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addContact(rs, map.get(uuid));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(strSql3)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addSection(rs, map.get(uuid));
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


    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        String strSql = "" +
                "DELETE FROM contact" +
                " WHERE resume_uuid = ?";
        sqlHelper.execute(conn, strSql, resume.getUuid());
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        String strSql = "" +
                "DELETE FROM section" +
                " WHERE resume_uuid = ?";
        sqlHelper.execute(conn, strSql, resume.getUuid());
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
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

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        String strSql = "" +
                "INSERT INTO section (resume_uuid, type, value)" +
                " VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(strSql)) {
            String uuid = resume.getUuid();
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                ps.setString(1, uuid);
                SectionType section = entry.getKey();
                ps.setString(2, section.name());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(3, ((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entry.getValue();
                        List<String> items = listSection.getItems();
                        String value = String.join("\n", items);
                        ps.setString(3, value);
                        break;
//                    case EXPERIENCE:
//                    case EDUCATION:
//                        break;
                    default:
                        throw new StorageException("Calling an unknown section from SectionType");
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        if (Objects.nonNull(rs.getString("type"))) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            String value = rs.getString("value");
            resume.setContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        if (Objects.nonNull(rs.getString("type"))) {
            SectionType section = SectionType.valueOf(rs.getString("type"));
            switch (section) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.setSection(section, new TextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value = rs.getString("value");
                    resume.setSection(section, new ListSection(Arrays.asList(value.split("\n"))));
                    break;
//                    case EXPERIENCE:
//                    case EDUCATION:
//                        break;
                default:
                    throw new StorageException("Calling an unknown section from SectionType");
            }
        }
    }

}
