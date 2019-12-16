package ru.javaops.basejava.sql;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T executiveInterface(String strSql, SqlExecutive<T> sqlExecutive) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(strSql)) {
            return sqlExecutive.executive(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e.getMessage(), e);
            } else {
                throw new StorageException(e.getMessage(), e);
            }
        }
    }
}
