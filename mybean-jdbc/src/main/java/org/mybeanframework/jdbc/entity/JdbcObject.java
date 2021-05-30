package org.mybeanframework.jdbc.entity;

import org.mybeanframework.jdbc.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

/**
 * @author herenpeng
 * @since 2020-12-25 15:54
 */
public class JdbcObject {

    private Connection connection;

    private PreparedStatement statement;

    private ResultSet resultSet;
    
    private int result;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public void setStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public JdbcObject() {
    }

    public JdbcObject(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public JdbcObject(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    public JdbcObject(Connection connection, PreparedStatement statement, int result) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        JdbcObject that = (JdbcObject) object;
        return Objects.equals(connection, that.connection) &&
                Objects.equals(statement, that.statement) &&
                Objects.equals(resultSet, that.resultSet) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connection, statement, resultSet, result);
    }

    public void close() {
        JdbcUtils.close(connection, statement, resultSet);
    }
}
