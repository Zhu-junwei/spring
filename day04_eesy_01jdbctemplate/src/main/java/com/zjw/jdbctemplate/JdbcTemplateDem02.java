package com.zjw.jdbctemplate;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcTemplate的最基本用法
 * @author 朱俊伟
 */
public class JdbcTemplateDem02 {
    public static void main(String[] args) {

        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        JdbcTemplate jdbcTemplate = ac.getBean("jdbcTemplate", JdbcTemplate.class);
        //3、执行操作
        jdbcTemplate.execute("INSERT INTO account(name,money) values ('zccc',1000)");
    }
}
