package ru.javaops.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutive<T> {
    T executive(PreparedStatement ps) throws SQLException;
}