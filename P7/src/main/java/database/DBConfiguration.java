package database;

import java.sql.*;

public class DBConfiguration {
    public DBConfiguration() {
        launch();
    }
    public static void main(String[] args) {
        new DBConfiguration();
    }

    public void launch() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn;
        Statement stmt;

        String dropString = "DROP TABLE Game";
        String createString = "CREATE TABLE Game  "
                + "(player_x VARCHAR(255) NOT NULL, "
                + "player_o VARCHAR(255) NOT NULL, "
                + "date_time TIMESTAMP, "
                + "board VARCHAR(9),"
                + "ended BOOLEAN, "
                + "next_move INT, "
                + "CONSTRAINT game_pk PRIMARY KEY (player_x, player_o, date_time))";


        conn = dbConnection.getConnection();
        if (conn == null) return;
        try {
            stmt = conn.createStatement();

            System.out.println(" . . . . droping table Game");
            stmt.execute(dropString);
            System.out.println(" . . . . tables dropped\n");

            System.out.println(" . . . . creating table Game");
            stmt.execute(createString);
            System.out.println(" . . . . table Game created.");

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
