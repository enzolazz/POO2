package database;

import items.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBListing {
    private List<Item> list() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement query = null;
        ResultSet books, magazines;

        List<Item> items = new ArrayList<>();

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return new ArrayList<>();

            query = conn.createStatement();
            books = query.executeQuery(
                    "select titulo, autor, ano from Book order by titulo");

            while (books.next()) {
                items.add(new Book(books.getString(1),
                        books.getString(2),
                        books.getInt(3)));
            }
            books.close();
            query.close();

            query = conn.createStatement();
            magazines = query.executeQuery(
                    "select titulo, org, vol, nro, ano from Magazine order by titulo");

            while (magazines.next()) {
                items.add(new Magazine(magazines.getString(1),
                        magazines.getString(2),
                        magazines.getInt(3),
                        magazines.getInt(4),
                        magazines.getInt(5)));
            }
            magazines.close();
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

        return items;
    }

    public String getItems() {
        List<Item> result = list();
        String resultString = "";

        for (Item i : result)
            resultString = resultString.concat(i.description());
        return resultString;
    }

    public String getBooksStored() {
        List<Item> result = list();
        String resultString = "";

        for (Item i : result)
            if (i instanceof Book)
                resultString = resultString.concat(i.description());
        return resultString;
    }

    public String getMagazinesStored() {
        List<Item> result = list();
        String resultString = "";

        for (Item i : result)
            if (i instanceof Book)
                resultString = resultString.concat(i.description());
        return resultString;
    }
}


