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

    private  ApplicationContext ac ;

    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("bean.xml");
    }

    /**
     * 查询所有账户
     */
    @Test
    public void testFindAll(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        List<Account> accountList = accountService.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    /**
     * 根据账户id查询账户
     */
    @Test
    public void testFindAccountById(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    /**
     * 新建账户
     */
    @Test
    public void testSaveAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = new Account();
        account.setId(4);
        account.setName("zzz");
        account.setMoney(9999f);
        accountService.saveAccount(account);
    }

    /**
     * 更新账户信息
     */
    @Test
    public void testUpdateAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(4);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    /**
     * 删除账户
     */
    @Test
    public void testDeleteAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        accountService.deleteAccount(4);
    }
}
