package Repository.impl;

import Repository.DatabaseService;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class MyDatabase implements DatabaseService {
    private static MyDatabase instance;
    private final String url = "jdbc:postgresql://localhost:5432/Escape_Room";
    private final String username = "admin";
    private final String password = " ";
    private Connection conn;

    private MyDatabase() {
        try {
            this.conn = getConnection();
        } catch (PSQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    private Connection getConnection() throws PSQLException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(String query, Object... params) throws PSQLException {
        try {
            if(!query.toLowerCase().contains("insert"))
                return 0;
            return executeUpdate(query, params);
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ResultSet select(String query, Object... params) throws PSQLException {
        try {
            if(!query.toLowerCase().contains("select"))
                return null;
            return executeQuery(query, params);
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(String query, Object... params) throws PSQLException {
        try {
            if(!query.toLowerCase().contains("update"))
                return 0;
            return executeUpdate(query, params);
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String query, Object... params) throws PSQLException {
        try {
            if(!query.toLowerCase().contains("delete"))
                return 0;
            return executeUpdate(query, params);
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return 0;
        }
    }

    private ResultSet executeQuery(String query, Object... params) throws PSQLException {
        try {
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return null;
        }
    }

    private int executeUpdate(String query, Object... params) throws PSQLException {
        try {
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
            return 0;
        }
    }

    public void close() throws PSQLException {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
        }
    }
}
