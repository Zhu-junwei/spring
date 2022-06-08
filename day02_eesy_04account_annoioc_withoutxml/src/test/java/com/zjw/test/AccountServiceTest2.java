package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用Junit单元测试，测试我们的配置
 * Spring整合junit的配置 不用自己实例化容器
 *      1、导入spring整合junit的jar(坐标) spring-test
 *      2、使用Junit提供的一个注解把原有的main方法替换了，替换成spring提供的
 *          @RunWith
 *      3、告知spring的运行器，spring和ioc创建是基于xml还是注解的，并说明位置
 *          @ContextConfiguration
 *              locations:指定xml文件的位置，加上classpath关键字，表示类路径下
 *              classes:指定注解类所在的位置
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigurationTest.class)
public class AccountServiceTest2 {

    @Resource
    private IAccountService accountService;

    @Test
    public void testFindAll(){
        List<Account> accountList = accountService.findAllAccount();
        accountService.findAccountById(1);
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindAccountById(){
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSaveAccount(){
        Account account = new Account();
        account.setName("zaa");
        account.setMoney(9999f);
        accountService.saveAccount(account);
    }

    @Test
    public void testUpdateAccount(){
        Account account = accountService.findAccountById(4);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    @Test
    public void testDeleteAccount(){

        accountService.deleteAccount(4);

    }
}
