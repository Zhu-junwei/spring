package com.zjw.ui;

import com.zjw.dao.IAccountDao;
import com.zjw.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    /**
     *
     * 创建对象采取的策略时延迟加载的方式。也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。
     *
     * @param args
     */

     public static void main(String[] args) {

        //1.获取核心容器对象
//        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
         ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //2.根据id获取Bean对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        accountService.saveAccount();

        IAccountService accountService2 = (IAccountService) ac.getBean("accountService");
        System.out.println(accountService);
        System.out.println(accountService2);
        ac.close();

//        accountService.saveAccount();

     }
}
