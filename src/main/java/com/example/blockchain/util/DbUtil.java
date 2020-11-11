package com.example.blockchain.util;

import java.sql.*;

public class DbUtil {
    private String dbUrl = "jdbc:mysql://localhost/cookiedemo"+"?serverTimezone=GMT%2B8";
    private String dbUserName = "root";
    private String dbPassword = "123456";
    private String jdbcName = "com.mysql.cj.jdbc.Driver";
    private Connection connection = null;

    public Connection getConnection(){
        try {
            Class.forName(jdbcName);
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            System.out.println("数据库连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void closeCon() {
        if(!(connection ==null)) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public ResultSet resultSet(String sql){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean execure(String sql){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
