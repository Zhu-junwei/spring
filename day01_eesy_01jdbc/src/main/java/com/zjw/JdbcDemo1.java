package com.zjw;

import java.sql.*;

public class JdbcDemo1 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //注册驱动
//        Driver driver = new Driver();
//        DriverManager.registerDriver(driver);
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eesy_spring?useSSL=false&serverTimezone=Asia/Shanghai", "root", "123456");
        System.out.println(connection);
        //获取操作数据库的预处理对象
        PreparedStatement ps = connection.prepareCall("SELECT * FROM account");
        //执行sql,得到结果集
        ResultSet resultSet = ps.executeQuery();
        //遍历结果集
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }
        //释放资源
        resultSet.close();
        ps.close();
        connection.close();
    }
}
