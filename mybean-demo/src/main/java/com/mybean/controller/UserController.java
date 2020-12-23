package com.mybean.controller;

import com.mybean.domain.User;
import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.web.mvc.annotation.RequestParam;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.response.enums.ResponseTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author herenpeng
 * @since 2020-12-02 16:06
 */
@MyBean
@RequestPath("user")
public class UserController {

    @RequestPath("index")
    public String index(@RequestParam("username") String username) {
        return "index.html";
    }

    @RequestPath(value = "string", type = ResponseTypeEnum.JSON)
    public String string(@RequestParam("username") String username) {
        return username;
    }

    @RequestPath(value = "number", type = ResponseTypeEnum.JSON)
    public Integer number(HttpServletRequest request, HttpServletResponse response, @RequestParam("number") Integer number) {
        return number;
    }

    @RequestPath(value = "double", type = ResponseTypeEnum.JSON)
    public Double dou(@RequestParam("number") Double number) {
        return number;
    }

    @RequestPath(value = "character", type = ResponseTypeEnum.JSON)
    public Character character(@RequestParam("character") Character character) {
        return character;
    }

    @RequestPath(value = "bool", type = ResponseTypeEnum.JSON)
    public Boolean bool(@RequestParam("bool") Boolean bool) {
        return bool;
    }

    @RequestPath(value = "text", type = ResponseTypeEnum.TEXT)
    public User text() {
        User user = new User("池总","123456");
        return user;
    }

    @RequestPath(value = "json", type = ResponseTypeEnum.JSON)
    public User json() {
        User user = new User("池总", null, new Date());
        return user;
    }

    @RequestPath(value = "map", type = ResponseTypeEnum.JSON)
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "池总");
        map.put("password", "123456");
        map.put("enabled", true);
        return map;
    }

    @RequestPath(value = "list", type = ResponseTypeEnum.JSON)
    public List<User> list() {
        List<User> list = new ArrayList<>();
        list.add(new User("池总", "123456"));
        list.add(new User("刘老板", "111111"));
        list.add(new User("波波", "bbbbbb"));
        return list;
    }

    @RequestPath(value = "set", type = ResponseTypeEnum.JSON)
    public Set<User> set() {
        Set<User> Set = new HashSet<>();
        Set.add(new User("池总", "set:123456"));
        Set.add(new User("刘老板", "set:111111"));
        Set.add(new User("波波", "set:bbbbbb"));
        return Set;
    }

    @RequestPath(value = "listMap", type = ResponseTypeEnum.JSON)
    public List<Map<String, Object>> listMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "池总");
        map1.put("password", "123456");
        map1.put("enabled", true);
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "刘老板");
        map2.put("password", "111111");
        map2.put("enabled", false);
        map2.put("birthday", new Date());
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("username", "刘老板");
        map3.put("password", "111111");
        map3.put("money", new BigDecimal(100000000));
        map3.put("enabled", false);
        map3.put("person", new User("员工1", "123456"));
        List<User> list2 = new ArrayList<>();
        list2.add(new User("池总", "123456"));
        list2.add(new User("刘老板", "111111"));
        list2.add(new User("波波", "bbbbbb"));
        map3.put("list2", list2);
        list.add(map3);
        return list;
    }


    @RequestPath(value = "mapParam", type = ResponseTypeEnum.JSON)
    public Map<String, Object> mapParam(Map<String, Object> map) {
        return map;
    }

}
