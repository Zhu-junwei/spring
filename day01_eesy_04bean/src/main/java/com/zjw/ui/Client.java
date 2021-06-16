package com.zjw.ui;

import com.zjw.domain.Life;
import com.zjw.factory.InstanceFactory;
import com.zjw.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.GC;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        //1.获取核心容器对象
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//        ApplicationContext ac = new FileSystemXmlApplicationContext("D:\\系统文件夹\\桌面\\bean.xml");

        //2.根据id获取Bean对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        System.out.println(accountService);
        accountService.saveAccount();
        System.out.println(ac.isSingleton("accountService"));


        //我直接实例化工厂
//        InstanceFactory instanceFactory = new InstanceFactory();
//        IAccountService service = instanceFactory.getAccountService();
//        service.saveAccount();


        ac.close();
        System.out.println("main 方法结束了。。。。");

    }
}
