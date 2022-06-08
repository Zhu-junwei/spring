package com.zjw.ui;

import com.zjw.factory.BeanFactory;
import com.zjw.service.IAccountService;

/**
 * 模拟一个表现层，用于调用业务层
 * 通过工厂类创建对象，需要创建的对象放在配置中间中
 * @author 朱俊伟
 */
public class Client {

    public static void main(String[] args) {

        for (int i=1 ;i<5;i++){
            IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
            System.out.println(accountService);
            accountService.saveAccount();
        }
    }
}
