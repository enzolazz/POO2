package database;

import java.sql.*;
import exceptions.KeyAlreadyStored;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import game.Game;
public class DBPopulation {
    public void insert(Game game) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement stmt;
        PreparedStatement psInsert;

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return;

            stmt = conn.createStatement();

            psInsert = conn.prepareStatement("insert into "
                    + "Game(player_x, player_o, date_time, board, ended, next_move) " + "values (?, ?, ?, ?, ?, ?)");

            psInsert.setString(1, game.getPlayerX());
            psInsert.setString(2, game.getPlayerO());
            psInsert.setTimestamp(3, game.getDateTime());
            psInsert.setString(4, game.getBoard());
            psInsert.setBoolean(5, game.gameHasEnded());
            psInsert.setInt(6, game.getNext_move());
            psInsert.executeUpdate();

            psInsert.close();
            stmt.close();
            conn.close();
            System.out.println("Closed connection");

            if (dbConnection.driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
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

        } catch (DerbySQLIntegrityConstraintViolationException e) {
            throw new KeyAlreadyStored("Chave primária existente no banco de dados!");
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }
}

