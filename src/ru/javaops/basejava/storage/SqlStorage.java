package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.SqlHelper;

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
        String strSql = "DELETE FROM resume";
        sqlHelper.executiveInterface(strSql, ps -> {
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
        return sqlHelper.executiveInterface(strSql, ps -> {
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
        String strSql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        String strSql2 = "UPDATE resume ";
        sqlHelper.executiveInterface(strSql, ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String strSql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.executiveInterface(strSql, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });

        strSql = "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)";
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            sqlHelper.executiveInterface(strSql, ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.execute();
                return null;
            });
        }
    }

    @Override
    public void delete(String uuid) {
        String strSql = "DELETE FROM resume WHERE resume.uuid = ?";
        sqlHelper.executiveInterface(strSql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        String strSql = "SELECT * FROM resume ORDER BY resume.full_name, resume.uuid";
        return sqlHelper.executiveInterface(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        String strSql = "SELECT COUNT(*) FROM resume";
        return sqlHelper.executiveInterface(strSql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
