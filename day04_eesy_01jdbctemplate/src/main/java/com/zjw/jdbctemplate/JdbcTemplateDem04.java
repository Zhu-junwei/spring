package com.zjw.jdbctemplate;


import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JdbcTemplate的最基本用法
 */
public class JdbcTemplateDem04 {
    public static void main(String[] args) {
        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);
        Account account = accountDao.findAccountById(1);
        System.out.println(account);
        account.setMoney(3000F);
        accountDao.updateAccount(account);
    }
}
