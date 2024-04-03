package database;

import game.Game;

import java.sql.*;


public class DBUpdating {

    public void update(Game game) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement stmt;
        PreparedStatement psUpdate;

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return;

            // ## INITIAL SQL SECTION ##
            // Create a statement to issue simple commands.
            stmt = conn.createStatement();

            // Prepare the insert statement to use
            psUpdate = conn.prepareStatement("update Game "
                    + "set ended = true "
                    + "where "
                    + "player_x = ? and "
                    + "player_o = ? and "
                    + "date_time = ?");

            psUpdate.setString(1, game.getPlayerX());
            psUpdate.setString(2, game.getPlayerO());
            psUpdate.setTimestamp(3, game.getDateTime());
            psUpdate.executeUpdate();
            psUpdate.close();

            psUpdate = conn.prepareStatement("update Game "
                    + "set board = ? "
                    + "where "
                    + "player_x = ? and "
                    + "player_o = ? and "
                    + "date_time = ?");

            psUpdate.setString(1, game.getBoard());
            psUpdate.setString(2, game.getPlayerX());
            psUpdate.setString(3, game.getPlayerO());
            psUpdate.setTimestamp(4, game.getDateTime());
            psUpdate.close();

            stmt.close();
            conn.close();
            System.out.println("Closed connection");

            // ## DATABASE SHUTDOWN SECTION ##
            /***
             * In embedded mode, an application should shut down Derby. Shutdown throws the
             * XJ015 exception to confirm success.
             ***/
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

            // Beginning of the primary catch block: prints stack trace
        } catch (
                Throwable e) {
            /*
             * Catch all exceptions and pass them to the Throwable.printStackTrace method
             */
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }
}

