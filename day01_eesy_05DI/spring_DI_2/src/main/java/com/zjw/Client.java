package com.zjw;

import com.zjw.construct.IAccountServiceA;
import com.zjw.construct.IAccountServiceB;
import com.zjw.setter.IAccountServiceC;
import com.zjw.setter.IAccountServiceD;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {

        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        System.out.println("*****通过构造方法依赖注入*****");
        IAccountServiceA accountServiceA = (IAccountServiceA) ac.getBean("accountServiceA");
        IAccountServiceB accountServiceB = (IAccountServiceB) ac.getBean("accountServiceB");
        System.out.println("accountServiceA = " + accountServiceA);
        System.out.println("accountServiceB = " + accountServiceB);

        System.out.println("*****通过setter方法依赖注入*****");
        IAccountServiceC accountServiceC = (IAccountServiceC) ac.getBean("accountServiceC");
        IAccountServiceD accountServiceD = (IAccountServiceD) ac.getBean("accountServiceD");
        System.out.println("accountServiceC = " + accountServiceC);
        System.out.println("accountServiceD = " + accountServiceD);

    }
}
