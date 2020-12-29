package com.mybean.service;

import com.mybean.domain.User;

import java.util.List;

/**
 * @author herenpeng
 * @since 2020-12-29 11:46
 */
public interface UserService {

    /**
     * 获取所有用户数据
     *
     * @return
     */
    List<User> list();

    /**
     * 逻辑删除一条用户数据
     *
     * @param id 用户主键
     */
    void logicDelete(Integer id);

    /**
     * 逻辑恢复一条用户数据
     *
     * @param id 用户主键
     */
    void logicRecover(Integer id);

    /**
     * 彻底删除一条用户数据
     *
     * @param id 用户主键
     */
    void delete(Integer id);


}
