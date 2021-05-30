package com.mybean.service.impl;

import com.mybean.domain.User;
import com.mybean.mapper.UserMapper;
import com.mybean.service.UserService;
import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.core.annotation.SetBean;

import java.util.List;

/**
 * @author herenpeng
 * @since 2020-12-29 11:49
 */
@MyBean
public class UserServiceImpl implements UserService {

    @SetBean
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void logicDelete(Integer id) {
        userMapper.logicDelete(id);
    }

    @Override
    public void logicRecover(Integer id) {
        userMapper.logicRecover(id);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
