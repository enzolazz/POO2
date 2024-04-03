package database;

import game.Game;
import java.sql.*;

public class DBListing {
    String allGames = "select player_x, player_o, date_time, board, ended, next_move from Game order by date_time desc";
    public Game listFirst() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement query = null;
        ResultSet gamesQuery;

        Game game = null;

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return null;

            query = conn.createStatement();

            gamesQuery = query.executeQuery(allGames);

            if (gamesQuery.next())
                game = new Game(gamesQuery.getString(1),
                        gamesQuery.getString(2),
                        gamesQuery.getTimestamp(3),
                        gamesQuery.getString(4),
                        gamesQuery.getBoolean(5),
                        gamesQuery.getInt(6));

            gamesQuery.close();
            query.close();
            conn.close();
            System.out.println("Closed connection");

            if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
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
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }

        return game;
    }
}


