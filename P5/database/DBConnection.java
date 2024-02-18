package database;

import java.sql.*;
import java.util.*;

public class DBConnection {
    String driver;
    String dbName = "Listagem";
    Properties props = new Properties();
    String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    public DBConnection(String driver) {
        this.driver = driver;
    }

    public Connection getConnection() {
        Connection conn = null;
        props.put("user", "App");
        props.put("password", "");

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connectionURL, props);
            System.out.println("Connected to database " + dbName);
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
        return conn;
    }

    public Connection getConnection(boolean check) throws SQLException {
        Connection conn = getConnection();
        if (check && conn != null)
            checkConnection(conn);
        return conn;
    }

    public void checkConnection(Connection conTst) throws SQLException {
        try {
            Statement s = conTst.createStatement();
            s.execute("update Book set titulo = '1' where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05")) {
                System.out.println("Table does not exist");
                throw sqle;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("Incorrect table definition. Drop tables and reconfigure");
                throw sqle;
            } else {
                System.out.println("Unhandled SQLException");
                throw sqle;
            }
        }
        System.out.println("Checking Connection: ok - table exists");
    }

}
