package com.hrp.controller;

import com.hrp.pojo.User;
import com.hrp.service.UserService;
import com.mybean.annotation.GetBean;
import com.mybean.annotation.MyBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hrp
 * @Date 2020/2/4 14:33
 */
@MyBean("user")
public class UserController {

    @GetBean
    private UserService userService;

    public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = userService.findAll();
        for (User user : list) {
            System.out.println(user);
        }
        return "my.html";
    }

    public List<User> findAll(){
        List<User> list = userService.findAll();
        for (User user : list) {
            System.out.println(user);
        }
        return list;
    }


}
