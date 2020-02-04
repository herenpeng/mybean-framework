package com.hrp.service;

import com.hrp.dao.UserDao;
import com.hrp.pojo.User;
import com.mybean.annotation.GetBean;
import com.mybean.annotation.MyBean;

import java.util.List;

/**
 * @author hrp
 */
@MyBean
public class UserService {

    @GetBean
    private UserDao userDao;

    public List<User> findAll(){
        return userDao.findAll();
    }

}
