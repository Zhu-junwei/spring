package com.zjw.ui;

import com.zjw.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {

        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        System.out.println("*****accountService1……通过构造方法依赖注入*****");
        IAccountService accountService1 = (IAccountService) ac.getBean("accountService1");
        accountService1.saveAccount();

        System.out.println("*****accountService2……通过setter方法依赖注入*****");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService2");
        accountService2.saveAccount();

        System.out.println("*****accountService3……复杂类型的注入/集合类型的注入*****");
        IAccountService accountService3 = (IAccountService) ac.getBean("accountService3");
        accountService3.saveAccount();

    }
}
