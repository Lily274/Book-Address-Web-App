package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDB {

    private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    private final static String DB_USER = "root";
    private final static String DB_PASSWD = "cibc0908!";

    public Connection getConn() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(DB_DRIVER);  // register
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            //System.out.println(conn.getCatalog());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
