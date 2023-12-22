package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用@RunWith测试，测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/*.xml"})
public class AccountServiceTest2 {

    @Resource
    private IAccountService accountService;

    /**
     * 查询所有账户
     */
    @Test
    public void testFindAll(){
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
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    /**
     * 新建账户
     */
    @Test
    public void testSaveAccount(){
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
        accountService.deleteAccount(4);
    }
}
