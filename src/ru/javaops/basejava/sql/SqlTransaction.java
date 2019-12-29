package ru.javaops.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction<T> {
    T executive(Connection conn) throws SQLException;
}
