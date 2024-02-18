package database;

import java.sql.*;

public class DBConfiguration {
    public static void main(String[] args) {
        new DBConfiguration().launch();
    }

    public void launch() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn;
        Statement stmt;

        String dropBookString   = "DROP TABLE Book";
        String createBookString = "CREATE TABLE Book  "
                + "(titulo VARCHAR(60) NOT NULL, "
                + "autor VARCHAR(255) NOT NULL, "
                + "ano INT NOT NULL, "
                + "CONSTRAINT book_pk PRIMARY KEY (titulo, autor))";

        String dropMagazineString   = "DROP TABLE Magazine";
        String createMagazineString = "CREATE TABLE Magazine  "
                + "(titulo VARCHAR(60) NOT NULL, "
                + "org VARCHAR(255) NOT NULL, "
                + "vol INT NOT NULL, "
                + "nro INT NOT NULL, "
                + "ano INT NOT NULL, "
                + "CONSTRAINT magazine_pk PRIMARY KEY (titulo, org))";


        conn = dbConnection.getConnection();
        if (conn == null) return;
        try {
            stmt = conn.createStatement();

            System.out.println(" . . . . droping table Book");
            stmt.execute(dropBookString);
            System.out.println(" . . . . droping table Magazine");
            stmt.execute(dropMagazineString);
            System.out.println(" . . . . tables dropped\n");

            System.out.println(" . . . . creating table Book");
            stmt.execute(createBookString);
            System.out.println(" . . . . table Book created.");

            System.out.println(" . . . . creating table Magazine");
            stmt.execute(createMagazineString);
            System.out.println(" . . . . table Book created.");

            stmt.close();
            conn.close();
            System.out.println("Closed connection");

            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("XJ015")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database shut down normally");
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }
}
