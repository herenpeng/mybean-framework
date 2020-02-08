package com.hrp.dao.impl;

import com.hrp.dao.UserDao;
import com.hrp.pojo.User;
import com.hrp.util.DataSource;
import com.mybean.annotation.GetBean;
import com.mybean.annotation.MyBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hrp
 * @date 2020/2/6 11:48
 */
@MyBean
public class UserDaoImpl implements UserDao {

    @GetBean
    private DataSource dataSource;

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            String sql = "select * from tb_user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataSource.close(conn,ps,rs);
        }
        return list;
    }
}
