package Repository;

import org.postgresql.util.PSQLException;

import java.sql.ResultSet;

public interface DatabaseService {
    int insert(String query, Object... params) throws PSQLException;
    ResultSet select(String query, Object... params) throws PSQLException;
    int update(String query, Object... params) throws PSQLException;
    int delete(String query, Object... params) throws PSQLException;
}
