package com.hrp.controller;

import com.hrp.pojo.User;
import com.hrp.service.UserService;
import com.mybean.annotation.GetBean;
import com.mybean.annotation.MyBean;

import java.util.List;

/**
 * @author hrp
 * @Date 2020/2/4 14:33
 */
@MyBean
public class UserController {

    @GetBean
    private UserService userService;

    public List<User> findAll(){
        return userService.findAll();
    }


}
