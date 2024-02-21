package database;

import java.sql.*;

import exceptions.KeyAlreadyStored;
import items.*;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class DBPopulation {
    public void insert(Item item) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement stmt;
        PreparedStatement psInsert;

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return;

            stmt = conn.createStatement();

            if (item instanceof Magazine) {
                psInsert = returnInsert((Magazine) item, conn);
            } else if (item instanceof Book) {
                psInsert = returnInsert((Book) item, conn);
            } else {
                throw new IllegalArgumentException("Unsupported item type: " + item.getClass().getName());
            }

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

    private PreparedStatement returnInsert(Magazine magazine, Connection conn) throws SQLException {
        PreparedStatement psInsert = conn.prepareStatement("insert into "
                + "Magazine(titulo, org, vol, nro, ano) " + "values (?, ?, ?, ?, ?)");

        psInsert.setString(1, magazine.getTitulo());
        psInsert.setString(2, magazine.getOrg());
        psInsert.setInt(3, magazine.getVol());
        psInsert.setInt(4, magazine.getNro());
        psInsert.setInt(5, magazine.getAno());

        return psInsert;
    }

    private PreparedStatement returnInsert(Book book, Connection conn) throws SQLException {
        PreparedStatement psInsert = conn.prepareStatement("insert into "
                + "Book(titulo, autor, ano) " + "values (?, ?, ?)");

        psInsert.setString(1, book.getTitulo());
        psInsert.setString(2, book.getAutor());
        psInsert.setInt(3, book.getAno());

        return psInsert;
    }
}

