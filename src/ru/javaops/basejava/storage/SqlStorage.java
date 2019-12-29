package ru.javaops.basejava.storage;

import org.intellij.lang.annotations.Language;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        @Language("PostgreSQL") String strSql = "DELETE FROM resume";
        sqlHelper.executive(strSql, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        @Language("PostgreSQL") String strSql = "" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN contact c " +
                "     ON r.uuid = c.resume_uuid " +
                "  WHERE r.uuid =?";
        return sqlHelper.executive(strSql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.setContact(type, value);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        @Language("PostgreSQL") final String strSql1 = "" +
                "UPDATE resume SET full_name = ?" +
                " WHERE uuid = ?";

        @Language("PostgreSQL") final String strSql2 = "" +
                "UPDATE contact SET value = ?" +
                " WHERE resume_uuid = ? AND type = ?";

        sqlHelper.transactionalExecutive(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(strSql1)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(strSql2)) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, entry.getValue());
                    ps.setString(2, resume.getUuid());
                    ps.setString(3, entry.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        @Language("PostgreSQL") final String strSql1 = "" +
                "INSERT INTO resume (uuid, full_name)" +
                " VALUES (?,?)";

        @Language("PostgreSQL") final String strSql2 = "" +
                "INSERT INTO contact (resume_uuid, type, value)" +
                " VALUES (?,?,?)";

        sqlHelper.transactionalExecutive(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(strSql1)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement(strSql2)) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, entry.getKey().name());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        @Language("PostgreSQL") String strSql = "" +
                "DELETE FROM resume" +
                " WHERE resume.uuid = ?";
        sqlHelper.executive(strSql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    //        Первый вариант getAllSorted()
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        @Language("PostgreSQL") String strSql = "" +
                "SELECT * FROM resume" +
                " ORDER BY resume.full_name, resume.uuid";
        sqlHelper.executive(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumes;
        });

        strSql = "" +
                "SELECT * FROM contact" +
                " WHERE contact.resume_uuid = ?";
        sqlHelper.executive(strSql, ps -> {
            for (Resume resume : resumes) {
                ps.setString(1, resume.getUuid());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resume.setContact(type, value);
                }
            }
            return resumes;
        });
        return resumes;
    }

//        Второй вариант getAllSorted()
/*
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        @Language("PostgreSQL") String strSql = "" +
                "SELECT * FROM resume" +
                " ORDER BY resume.full_name, resume.uuid";
        sqlHelper.executive(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumes;
        });

        strSql = "SELECT * FROM contact ORDER BY contact.resume_uuid";
        sqlHelper.executive(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                String value = rs.getString("value");
                for (Resume resume : resumes) {
                    if (resume.getUuid().equals(uuid)) {
                        resume.setContact(type, value);
                    }
                }
            }
            return resumes;
        });
        return resumes;
    }
*/


//    Третий вариант getAllSorted()
/*
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        @Language("PostgreSQL") String strSql = "" +
                "    SELECT * FROM resume" +
                " LEFT JOIN contact " +
                "        ON resume.uuid = contact.resume_uuid " +
                "  ORDER BY resume.full_name, resume.uuid";
        sqlHelper.executive(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            boolean notEndQuery = false;
            if (rs.next()) {
                notEndQuery = true;
            }
            while (notEndQuery) {
                String uuid = rs.getString("uuid").trim();
                String full_name = rs.getString("full_name");
                Resume resume = new Resume(uuid, full_name);
                while (uuid.equals(rs.getString("uuid").trim())) {
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resume.setContact(type, value);
                    if (!rs.next()) {
                        notEndQuery = false;
                        break;
                    }
                }
                resumes.add(resume);
            }
            return resumes;
        });
        return resumes;
    }
*/

    @Override
    public int size() {
        @Language("PostgreSQL") String strSql = "" +
                "SELECT COUNT(*)" +
                "  FROM resume";
        return sqlHelper.executive(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
