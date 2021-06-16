package com.zjw.ui;

import com.zjw.service.IAccountService;
import com.zjw.service.impl.AccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.UUID;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {

        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//        ApplicationContext ac = new FileSystemXmlApplicationContext("D:\\系统文件夹\\桌面\\bean.xml");

        //2.根据id获取Bean对象
        System.out.println("*****accountService1*****");
        IAccountService accountService1 = (IAccountService) ac.getBean("accountService1");
        accountService1.saveAccount();

        System.out.println("*****accountService2*****");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService2");
        accountService2.saveAccount();

        System.out.println("*****accountService3*****");
        IAccountService accountService3 = (IAccountService) ac.getBean("accountService3");
        accountService3.saveAccount();

    }
}
