package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 使用Junit单元测试，测试我们的配置
 */
public class AccountServiceTest {

    private ApplicationContext ac ;

    /**
     * 1. 初始化容器
     */
    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("bean.xml");
    }

    @Test
    public void testFindAll(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        List<Account> accountList = accountService.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindAccountById(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);

    }

    @Test
    public void testSaveAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = new Account();
        account.setId(7);
        account.setName("abc");
        account.setMoney(9999f);
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdateAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    @Test
    public void testDeleteAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        accountService.deleteAccount(7);
    }
}
