package org.mybeanframework.jdbc.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author herenpeng
 * @since 2020-12-25 14:44
 */
public class JdbcUtils {

    private static DruidDataSource druidDataSource;


    static {

        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(JdbcXmlHelper.getDriverName());
        druidDataSource.setUrl(JdbcXmlHelper.getUrl());
        druidDataSource.setUsername(JdbcXmlHelper.getUsername());
        druidDataSource.setPassword(JdbcXmlHelper.getPassword());
    }


    /**
     * 获取数据源
     *
     * @return
     */
    public static DataSource getDataSource() {
        return druidDataSource;
    }

    /**
     * 从链接池中获取链接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return druidDataSource.getConnection().getConnection();
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
