package com.hrp.util;

import com.mybean.annotation.MyBean;

import java.sql.*;

/**
 * @author hrp
 */
@MyBean
public class DataSource {

    private final String CLASS_NAME = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/db_database08";
    private final String USERNAME = "root";
    private final String PASSWORD = "admin";

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if(rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(Connection conn, PreparedStatement ps) {
        try {
            if(ps != null){
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
