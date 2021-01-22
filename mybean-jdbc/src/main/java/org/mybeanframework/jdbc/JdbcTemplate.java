package org.mybeanframework.jdbc;

import org.mybeanframework.common.util.ClassUtils;
import org.mybeanframework.common.util.MethodUtils;
import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.jdbc.entity.JdbcObject;
import org.mybeanframework.jdbc.util.JdbcUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Jdbc模板方法
 *
 * @author herenpeng
 * @since 2020-12-25 14:23
 */
@MyBean
public class JdbcTemplate<E> {

    /**
     * 有参数的SQL预处理方法
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @return
     * @throws SQLException
     */
    private JdbcObject pretreatmentQuerySql(String sql, Object[] params) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            String param = String.valueOf(params[i]);
            ps.setString(i + 1, param);
        }
        ResultSet rs = ps.executeQuery();
        return new JdbcObject(conn, ps, rs);
    }

    /**
     * 无参数的SQL预处理方法
     *
     * @param sql SQL
     * @return
     * @throws SQLException
     */
    private JdbcObject pretreatmentQuerySql(String sql) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return new JdbcObject(conn, ps, rs);
    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private JdbcObject pretreatmentUpdateSql(String sql, Object[] params) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            String param = String.valueOf(params[i]);
            ps.setString(i + 1, param);
        }
        int result = ps.executeUpdate();
        return new JdbcObject(conn, ps, result);
    }

    /**
     * 有参数查询List
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @param clazz  字节码对象
     * @return
     */
    public List<E> selectList(String sql, Object[] params, Class<E> clazz) {
        JdbcObject jdbcObject = null;
        List<E> list = null;
        try {
            jdbcObject = pretreatmentQuerySql(sql, params);
            ResultSet rs = jdbcObject.getResultSet();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getObject(rs, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcObject.close();
        }
        return list;
    }


    /**
     * 无参数查询List
     *
     * @param sql   SQL
     * @param clazz 字节码对象
     * @return
     */
    public List<E> selectList(String sql, Class<E> clazz) {
        JdbcObject jdbcObject = null;
        List<E> list = null;
        try {
            jdbcObject = pretreatmentQuerySql(sql);
            ResultSet rs = jdbcObject.getResultSet();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(getObject(rs, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcObject.close();
        }
        return list;
    }

    /**
     * 有参数查询一条记录，多条记录默认查询第一条
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @param clazz  字节码对象
     * @return
     */
    public E selectOne(String sql, Object[] params, Class<E> clazz) {
        E result = null;
        JdbcObject jdbcObject = null;
        try {
            jdbcObject = pretreatmentQuerySql(sql, params);
            ResultSet rs = jdbcObject.getResultSet();
            if (rs.next()) {
                result = getObject(rs, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcObject.close();
        }
        return result;
    }

    /**
     * 无参数查询一条记录，多条记录默认查询第一条
     *
     * @param sql   SQL
     * @param clazz 字节码对象
     * @return
     */
    public E selectOne(String sql, Class<E> clazz) {
        E result = null;
        JdbcObject jdbcObject = null;
        try {
            jdbcObject = pretreatmentQuerySql(sql);
            ResultSet rs = jdbcObject.getResultSet();
            if (rs.next()) {
                result = getObject(rs, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcObject.close();
        }
        return result;
    }


    /**
     * 通过结果集获取对象实例
     *
     * @param resultSet 结果集
     * @param clazz     字节码对象
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    private E getObject(ResultSet resultSet, Class<E> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException, IntrospectionException {
        E object = clazz.newInstance();
        List<MethodUtils.BeanContent> writeMethodList = MethodUtils.getWriteMethodList(clazz);
        for (MethodUtils.BeanContent beanContent : writeMethodList) {
            String fieldName = beanContent.getProperties();
            Method method = beanContent.getMethod();
            Field field = clazz.getDeclaredField(fieldName);
            Class<?> fieldType = field.getType();
            Object result;
            try {
                if (ClassUtils.isString(fieldType)) {
                    result = resultSet.getString(fieldName);
                } else if (ClassUtils.isByte(fieldType)) {
                    result = resultSet.getByte(fieldName);
                } else if (ClassUtils.isShort(fieldType)) {
                    result = resultSet.getShort(fieldName);
                } else if (ClassUtils.isInteger(fieldType)) {
                    result = resultSet.getInt(fieldName);
                } else if (ClassUtils.isLong(fieldType)) {
                    result = resultSet.getLong(fieldName);
                } else if (ClassUtils.isFloat(fieldType)) {
                    result = resultSet.getFloat(fieldName);
                } else if (ClassUtils.isDouble(fieldType)) {
                    result = resultSet.getDouble(fieldName);
                } else if (ClassUtils.isBoolean(fieldType)) {
                    result = resultSet.getBoolean(fieldName);
                } else if (ClassUtils.isDate(fieldType)) {
                    result = resultSet.getDate(fieldName);
                } else {
                    result = resultSet.getString(fieldName);
                }
            } catch (SQLException e) {
                result = null;
            }
            method.invoke(object, result);
        }
        return object;
    }

    /**
     * 更新方法
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @return
     */
    public int update(String sql, Object[] params) {
        int result = 0;
        JdbcObject jdbcObject = null;
        try {
            jdbcObject = pretreatmentUpdateSql(sql, params);
            result = jdbcObject.getResult();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcObject.close();
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @return
     */
    public int delete(String sql, Object[] params) {
        return update(sql, params);
    }

    /**
     * 插入方法
     *
     * @param sql    SQL
     * @param params SQL参数数组
     * @return
     */
    public int insert(String sql, Object[] params) {
        return update(sql, params);
    }

}
