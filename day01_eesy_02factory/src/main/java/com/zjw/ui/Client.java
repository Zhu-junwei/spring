package com.zjw.ui;

import com.zjw.factory.BeanFactory;
import com.zjw.service.IAccountService;
import com.zjw.service.impl.AccountServiceImpl;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {
//        IAccountService accountService = new AccountServiceImpl();
        for (int i=1 ;i<5;i++){
            IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
            System.out.println(accountService);
            accountService.saveAccount();
        }
//        accountService.saveAccount();
    }
}
