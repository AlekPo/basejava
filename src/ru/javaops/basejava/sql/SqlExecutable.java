package ru.javaops.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutable<T> {
    T execute(PreparedStatement ps) throws SQLException;
}