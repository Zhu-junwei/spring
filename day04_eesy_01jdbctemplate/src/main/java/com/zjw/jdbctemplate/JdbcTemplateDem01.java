package com.zjw.jdbctemplate;


import com.zjw.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

/**
 * @author zjw
 */
public class JdbcTemplateDem01 {
    public static void main(String[] args) {
        //准备数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&serverTimeZone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        //1、创建JdbcTemplate对象
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        //2、执行操作
        //插入
//        jt.execute("INSERT INTO account(name,money) values ('zbbb',1000)");
        //查询
        List<Account> accounts = jt.query("SELECT * FROM account ", new BeanPropertyRowMapper<Account>(Account.class));
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
