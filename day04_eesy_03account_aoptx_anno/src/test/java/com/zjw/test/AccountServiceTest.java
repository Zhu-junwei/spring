package com.zjw.test;

import com.zjw.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
    private IAccountService as;

    @Test
    public void testTransfer() {
//        List<Account> accountList = as.findAllAccount();
//        for (Account account : accountList) {
//            System.out.println(account);
//        }
        as.transfer("aaa", "bbb", 100f);
    }
}