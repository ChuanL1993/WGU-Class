/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is DBConnection class. This class create connection to date base as well
 * as close to connection
 *
 * @author Chuan
 */
public class DBConnection {

    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ082i9";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJDBCDDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U082i9";
    private static final String password = "53689198281";
    private static Connection conn = null;

    /**
     * This method create connection to Database
     *
     * @return connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Successful");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getStackTrace());
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        return conn;
    }

    /**
     * This method close connection
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Closed!");
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }

    }

    /**
     * This method get connection for user when needed.
     *
     * @return connection
     */
    public static Connection getConnection() {
        return conn;
    }

}
