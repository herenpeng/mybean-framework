package com.mybean.test;

import com.mybean.domain.User;
import org.junit.Test;
import org.mybeanframework.common.util.JsonUtils;

/**
 * @author herenpeng
 * @since 2021-07-02 23:41
 */
public class JsonUtilsTest {


    @Test
    public void test01() {
        User user = new User();
        user.setUsername("池总");
        user.setPassword("11111");
        System.out.println(JsonUtils.toJson(user));;
    }
}
