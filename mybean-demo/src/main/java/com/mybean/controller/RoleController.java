package com.mybean.controller;

import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.web.mvc.annotation.RequestPath;

/**
 * @author herenpeng
 * @since 2020-12-24 15:17
 */
@MyBean
@RequestPath
public class RoleController {

    @RequestPath("/json")
    public String josn() {
        return "index.html";
    }

    @RequestPath("index")
    public String index() {
        return "index.html";
    }

    @RequestPath
    public String empty() {
        return "index.html";
    }

    @RequestPath("/")
    public String url() {
        return "index.html";
    }

}
