[toc]

# Spring创建Bean的三种方式及Bean的生命周期

## Spring创建Bean的三种方式

### 第一种方式：使用默认构造函数创建

> 在spring的配置文件中使用bean标签，配以id和class属性之后，且没有其他属性和标签时。
> 采用的就是默认构造函数创建bean对象，此时如果类中没有默认构造函数，则对象无法创建。

Spring配置文件
```xml
<bean id="accountServiceOne" class="com.zjw.service.impl.AccountServiceOneImpl" />
```
Java类
```java
package com.zjw.service.impl;

import com.zjw.service.IAccountServiceOne;

/**
 * 账户的业务层实现类
 * 对象创建的三种方式一：通过构造方法创建对象
 */
public class AccountServiceOneImpl implements IAccountServiceOne {
    
    public AccountServiceOneImpl() {
        System.out.println("AccountServiceOneImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceOneImpl中的saveAccount方法执行了");
    }
}
```
测试
```java

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args)  {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //对象创建的三种方式一：通过构造方法创建对象
        IAccountServiceOne accountServiceOne = (IAccountServiceOne) ac.getBean("accountServiceOne");
        System.out.println(accountServiceOne);
        accountServiceOne.saveAccount();

        System.out.println("main 方法结束了。。。。");

    }
}
```
结果
```
AccountServiceOneImpl……我创建了。。。
com.zjw.service.impl.AccountServiceOneImpl@258e2e41
AccountServiceOneImpl中的saveAccount方法执行了
main 方法结束了。。。。
```

### 第二种方式：使用普通工厂中的方法创建对象

> 使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入spring容器）

Spring配置
```xml
    <!--第二种方式：使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入spring容器）-->
    <bean id="instanceFactory" class="com.zjw.factory.InstanceFactory"/>
    <bean id="accountServiceTwo" factory-bean="instanceFactory" factory-method="getAccountService" />
```
工厂类
```java
/**
 * @author zjw
 */
public class InstanceFactory {

    public IAccountServiceTwo getAccountService(){
        return new AccountServiceTwoImpl();
    }
}
```
Java类
```java
/**
 * 账户的业务层实现类
 * 第二种方式：使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入spring容器）
 */
public class AccountServiceTwoImpl implements IAccountServiceTwo {
    
    public AccountServiceTwoImpl() {
        System.out.println("AccountServiceTwoImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceTwoImpl中的saveAccount方法执行了");
    }
}
```

测试
```java
/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args)  {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //第二种方式：使用普通工厂中的方法创建对象
        IAccountServiceTwo accountServiceTwo = ac.getBean("accountServiceTwo", IAccountServiceTwo.class);
        System.out.println(accountServiceTwo);
        accountServiceTwo.saveAccount();

        System.out.println("main 方法结束了。。。。");
    }
}
```
结果
```
AccountServiceTwoImpl……我创建了。。
com.zjw.service.impl.AccountServiceTwoImpl@258e2e41
AccountServiceTwoImpl中的saveAccount方法执行了
main 方法结束了。。。。
```

### 第三种方式：使用工厂中的静态方法创建对象

Spring配置
```xml
    <!--第三种方式：使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring）-->
    <bean id="accountServiceThree" class="com.zjw.factory.StaticFactory" factory-method="getAccountService" />
```
工厂类
```java

public class StaticFactory {
    public static IAccountServiceThree getAccountService(){
        return new AccountServiceThreeImpl();
    }
}
```
Java类
```java
/**
 * 账户的业务层实现类
 * 第三种方式：使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring）
 */
public class AccountServiceThreeImpl implements IAccountServiceThree {

    public AccountServiceThreeImpl() {
        System.out.println("AccountServiceThreeImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceThreeImpl中的saveAccount方法执行了");
    }
}
```

测试
```java
/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args)  {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        
        //第二种方式：使用工厂中的静态方法创建对象
        IAccountServiceThree accountServiceThree= ac.getBean("accountServiceThree", IAccountServiceThree.class);
        System.out.println(accountServiceThree);
        accountServiceThree.saveAccount();

        System.out.println("main 方法结束了。。。。");

    }
}
```
结果
```
AccountServiceThreeImpl……我创建了。。
com.zjw.service.impl.AccountServiceThreeImpl@258e2e41
AccountServiceThreeImpl中的saveAccount方法执行了
main 方法结束了。。。。
```

## Bean的生命周期

- 单例对象

**出生**：当容器创建时对象出生 

**活着**：只要容器还在，对象一直活着

**死亡**：容器销毁，对象给你消亡

> 总结：单例对象的生命周期和容器相同

- 多例对象

**出生**：当我们使用对象时spring框架为我们创建

**活着**：对象只要是在使用过程中就一直活着

**死亡**：当对象长时间不用，且没有别的对象引用时，由Java的垃圾回收器回收

Spring配置
```xml
    <!--
        init-method:对象创建后执行的方法
        destroy-method:对象销毁时执行的方法
            单例对象，容器关闭后执行
            多例模式，java虚拟机决定
        scope:单例还是多例对象
            singleton:默认，单例
            prototype:多例
    -->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl"
          init-method="init" destroy-method="destroy" />
```
Java类
```java
/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {
    
    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceImpl……saveAccount方法执行了");
    }

    public void init(){
        System.out.println("AccountServiceImpl……init方法执行了。。");
    }

    public void destroy(){
        System.out.println("AccountServiceImpl……destroy方法执行了。。");
    }
}
```

测试
```java
/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args)  {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

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
```

结果
```
AccountServiceImpl……我创建了。。
AccountServiceImpl……init方法执行了。。
com.zjw.service.impl.AccountServiceImpl@add0edd
AccountServiceImpl……saveAccount方法执行了
true
AccountServiceImpl……destroy方法执行了。。
com.zjw.service.impl.AccountServiceImpl@add0edd
main 方法结束了。。。。
```