package com.mybean.controller;

import com.mybean.domain.User;
import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.response.enums.ResponseTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author herenpeng
 * @since 2020-12-02 16:06
 */
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        System.out.println(username);
        return "index.html";
    }

    @RequestPath(value = "string", type = ResponseTypeEnum.OBJECT)
    public String string(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        return username;
    }

    @RequestPath(value = "object", type = ResponseTypeEnum.OBJECT)
    public User object(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setUsername("池总");
        user.setPassword("123456");
        return user;
    }

    @RequestPath(value = "json", type = ResponseTypeEnum.JSON)
    public User json(HttpServletRequest request, HttpServletResponse response) {
        User user = new User("池总", "123456");
        return user;
    }

    @RequestPath(value = "list", type = ResponseTypeEnum.JSON)
    public List<User> list(HttpServletRequest request, HttpServletResponse response) {
        List<User> list = new ArrayList<>();
        list.add(new User("池总", "123456"));
        list.add(new User("刘老板", "111111"));
        list.add(new User("波波", "bbbbbb"));
        return list;
    }

}
