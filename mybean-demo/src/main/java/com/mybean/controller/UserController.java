package com.mybean.controller;

import org.mybeanframework.core.annotation.MyBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author herenpeng
 * @since 2020-12-02 16:06
 */
@MyBean
public class UserController {

    public String hello(HttpServletRequest request, HttpServletResponse response) {
        return "index.html";
    }

}
