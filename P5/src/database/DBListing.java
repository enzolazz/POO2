package database;

import java.sql.*;

public class DBListing {
    String booksStored, magazinesStored;

    private void list() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement query = null;
        ResultSet books, magazines;

        this.booksStored = "";
        this.magazinesStored = "";

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return;

            query = conn.createStatement();
            books = query.executeQuery(
                    "select titulo, autor, ano from Book order by titulo");

            while (books.next()) {
                booksStored = booksStored.concat("Livro: " + books.getString(1) + ", "
                        + books.getString(2) + ", "
                        + books.getInt(3) + '\n');
            }
            books.close();
            query.close();

            query = conn.createStatement();
            magazines = query.executeQuery(
                    "select titulo, org, vol, nro, ano from Magazine order by titulo");

            while (magazines.next()) {
                magazinesStored = magazinesStored.concat("Revista: " + magazines.getString(1) + ", "
                        + magazines.getString(2) + ", "
                        + magazines.getInt(3) + ", "
                        + magazines.getInt(4) + ", "
                        + magazines.getInt(5) + '\n');
            }
            magazines.close();
            query.close();

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
    }

    public boolean checkConnection(Connection conTst) throws SQLException {
        try {
            Statement s = conTst.createStatement();
            s.execute("update Book set Titulo = '1' where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05")) return false;
            else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("Incorrect table definition. Drop table WISH_LIST and rerun this program");
                throw sqle;
            } else {
                System.out.println("Unhandled SQLException");
                throw sqle;
            }
        }
        System.out.println("Checking Connection: ok - table exists");
        return true;
    }

    public String getBooksStored() {
        list();
        return booksStored;
    }

    public String getMagazinesStored() {
        list();
        return magazinesStored;
    }
}

