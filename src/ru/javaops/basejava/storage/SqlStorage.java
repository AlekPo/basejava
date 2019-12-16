package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.sql.SqlExecutive;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String strSql = "DELETE FROM resume";
        sqlHelper.executiveInterface(strSql, (SqlExecutive<Void>) ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String strSql = "SELECT * FROM resume WHERE resume.uuid =?";
        return sqlHelper.executiveInterface(strSql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        String strSql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlHelper.executiveInterface(strSql, (SqlExecutive<Void>) ps -> {
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
        sqlHelper.executiveInterface(strSql, (SqlExecutive<Void>) ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        String strSql = "DELETE FROM resume WHERE resume.uuid = ?";
        sqlHelper.executiveInterface(strSql, (SqlExecutive<Void>) ps -> {
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
        String strSql = "SELECT * FROM resume ORDER BY resume.uuid";
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
            rs.next();
            return rs.getInt(1);
        });
    }
}
