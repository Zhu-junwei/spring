package com.zjw.jdbctemplate;


import com.zjw.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JdbcTemplate的CRUD操作
 *
 * @author 朱俊伟
 */
public class JdbcTemplateDem03 {
    public static void main(String[] args) {

        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        JdbcTemplate jdbcTemplate = ac.getBean("jdbcTemplate", JdbcTemplate.class);
        //3、执行操作
//        jdbcTemplate.execute("INSERT INTO account(name,money) values ('zccc',1000)");
        //保存
//        jdbcTemplate.update("INSERT INTO account(name,money)values(?,?)","zeee",333F);
        //更新
//        jdbcTemplate.update("UPDATE account SET name=?,money=? WHERE id =? ","zeee",9999F,9);
        //删除
//        jdbcTemplate.update("DELETE FROM account WHERE id =? ",8);
        //查询所有
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE money>?", new AccountRowMapper(), 1000f);
        //Spring 提供的封装BeanPropertyRowMapper
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE money > ?", new BeanPropertyRowMapper<>(Account.class), 1000f);
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account", new BeanPropertyRowMapper<>(Account.class));
//        for (Account account : accounts){
//            System.out.println(account);
//        }

        //查询一个
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE id=?", new BeanPropertyRowMapper<>(Account.class), 1);
//        System.out.println(accounts.isEmpty()?"没有内容":accounts.get(0));
        //查询返回一行一列（使用聚合函数，但不加group by子句）
//        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM account WHERE money>?", Long.class, 1000f);
//        System.out.println(count);
    }
}
class AccountRowMapper implements RowMapper<Account>{

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setMoney(resultSet.getFloat("money"));
        return account;
    }
}