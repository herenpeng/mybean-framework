package org.mybeanframework.jdbc.util;

import java.sql.*;

/**
 * @author herenpeng
 * @since 2020-12-25 14:44
 */
public class JdbcUtils {

    private static String driverClass = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        driverClass = JdbcXmlHelper.getDriverName();
        url = JdbcXmlHelper.getUrl();
        username = JdbcXmlHelper.getUsername();
        password = JdbcXmlHelper.getPassword();
    }


    /**
     * 从链接池中获取链接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * 重载方法，关闭链接
     *
     * @param connection
     * @param statement
     * @param resultSet
     * @throws SQLException
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(connection, statement);
        if (resultSet != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 重载方法，关闭链接
     *
     * @param connection
     * @param statement
     * @throws SQLException
     */
    public static void close(Connection connection, Statement statement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
