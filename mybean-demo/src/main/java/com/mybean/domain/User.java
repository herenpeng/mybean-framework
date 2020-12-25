package com.mybean.domain;

import org.mybeanframework.common.annotation.JsonDateFormat;
import org.mybeanframework.common.annotation.JsonNullIgnore;

import java.util.Date;

/**
 * @author herenpeng
 * @since 2020-12-21 11:32
 */
public class User {

    private String username;

    @JsonNullIgnore
    private String password;

    @JsonDateFormat
    private Date birthday;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Date birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
