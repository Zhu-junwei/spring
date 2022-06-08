package com.zjw.ui;

import com.zjw.service.IAccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层，用于调用业务层
 * @author 朱俊伟
 */
public class Client {

     public static void main(String[] args) {

        //1.获取核心容器对象
//        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
         //为什么不用父类的引用，因为后面要调用ac.close方法，父类ApplicationContext中没有这个方法
         ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //2.根据id获取Bean对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        accountService.saveAccount();

        IAccountService accountService2 = (IAccountService) ac.getBean("accountService");
        System.out.println(accountService);
        System.out.println(accountService2);
        ac.close();

     }
}
