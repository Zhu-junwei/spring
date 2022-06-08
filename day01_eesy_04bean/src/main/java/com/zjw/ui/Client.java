package com.zjw.ui;

import com.zjw.service.IAccountService;
import com.zjw.service.IAccountServiceOne;
import com.zjw.service.IAccountServiceThree;
import com.zjw.service.IAccountServiceTwo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层，用于调用业务层
 * @author 朱俊伟
 */
public class Client {

    public static void main(String[] args)  {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //对象创建的三种方式一：通过构造方法创建对象
        IAccountServiceOne accountServiceOne = (IAccountServiceOne) ac.getBean("accountServiceOne");
        System.out.println(accountServiceOne);
        accountServiceOne.saveAccount();

        //第二种方式：使用普通工厂中的方法创建对象
        IAccountServiceTwo accountServiceTwo = ac.getBean("accountServiceTwo",IAccountServiceTwo.class);
        System.out.println(accountServiceTwo);
        accountServiceTwo.saveAccount();

        //第二种方式：使用普通工厂中的方法创建对象
        IAccountServiceThree accountServiceThree= ac.getBean("accountServiceThree", IAccountServiceThree.class);
        System.out.println(accountServiceThree);
        accountServiceThree.saveAccount();

        //Bean的生命周期
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        System.out.println(accountService);
        accountService.saveAccount();
        System.out.println(ac.isSingleton("accountService"));

        ac.close();
        System.out.println(accountService);
        System.out.println("main 方法结束了。。。。");

    }
}
