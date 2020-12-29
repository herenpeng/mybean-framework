package com.mybean.mapper;

import com.mybean.domain.User;
import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.core.annotation.SetBean;
import org.mybeanframework.jdbc.JdbcTemplate;

import java.util.List;

/**
 * @author herenpeng
 * @since 2020-12-25 15:42
 */
@MyBean
public class UserMapper {

    @SetBean
    private JdbcTemplate jdbcTemplate;

    public List<User> list() {
        String sql = "select * from sys_user";
        List<User> list = jdbcTemplate.selectList(sql, User.class);
        return list;
    }

    public void logicDelete(Integer id) {
        String sql = "update sys_user set deleted = 1 where id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    public void logicRecover(Integer id) {
        String sql = "update sys_user set deleted = 0 where id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    public void delete(Integer id) {
        String sql = "delete from sys_user where id = ?";
        jdbcTemplate.delete(sql, new Object[]{id});
    }

}
