package com.mybean.domain;

/**
 * @author herenpeng
 * @since 2020-12-21 11:32
 */
public class User {

    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码，如果password为null，则在序列化为json的时候不进行序列化
     */
    private String password;

    /**
     * 账号是否启用，true为启用，false为禁用，默认为true
     */
    private Boolean enabled;

    /**
     * 账号是否锁定，true为锁定，false为未锁定，默认为false
     */
    private Boolean locked;

    /**
     * 账号是否过期，true为过期，false为未过期，默认为false
     */
    private Boolean accountExpire;

    /**
     * 密码是否过期，true为过期，false为未过期，默认为false
     */
    private Boolean passwordExpire;

    /**
     * 逻辑删除字段
     */
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getAccountExpire() {
        return accountExpire;
    }

    public void setAccountExpire(Boolean accountExpire) {
        this.accountExpire = accountExpire;
    }

    public Boolean getPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(Boolean passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
