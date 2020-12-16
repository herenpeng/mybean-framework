package com.mybean.controller;

import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.response.enums.ResponseTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author herenpeng
 * @since 2020-12-02 16:06
 */
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath("hello")
    public String hello(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        System.out.println(username);
        return "index.html";
    }

    @RequestPath(value = "data", type = ResponseTypeEnum.DATA)
    public String data(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        return username;
    }

}
