/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * This is DBQuery class. It create statement and preparedStatement for sql
 * statement
 *
 * @author heych
 */
public class DBQuery {

    private static Statement statement;

    private static PreparedStatement preparedStatement;

    /**
     * Method get preparedStatement
     *
     * @return preparedStatement
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * Method set preparedStatement
     *
     * @param conn connection
     * @param sqlStatement the sql statement
     * @throws SQLException sqlException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);

    }

    /**
     * Method get statement
     *
     * @return statement
     */
    public static Statement getStatemnt() {
        return statement;
    }

    /**
     * Method set statement
     *
     * @param conn connection
     * @throws SQLException sqlException
     */
    public static void setStatemnt(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

}
