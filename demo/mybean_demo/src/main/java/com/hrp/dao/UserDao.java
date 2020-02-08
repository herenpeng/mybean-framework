package com.hrp.dao;

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
 */

public interface UserDao {


    public List<User> findAll();

}
