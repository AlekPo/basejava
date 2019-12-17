package ru.javaops.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.StorageException;

import java.sql.SQLException;

class ExceptionUtil {
    private ExceptionUtil() {
    }

    static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {

//            https://www.postgresql.org/docs/12/errcodes-appendix.html

            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(e.getMessage(), e);
            }
        }
        return new StorageException(e.getMessage(), e);
    }
}
