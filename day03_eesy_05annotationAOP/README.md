# Spring 基于注解的AOP面向切面编程

[源码](https://github.com/Zhu-junwei/spring/tree/master/day03_eesy_05annotationAOP)

## 代码实现

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day03_eesy_05annotationAOP</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <encoding>UTF-8</encoding>
        <spring.version>6.1.1</spring.version>
        <aspectjweaver.version>1.9.21</aspectjweaver.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectjweaver.version}</version>
        </dependency>
    </dependencies>

</project>
```

Spring配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置spring创建容器时要扫描的包-->
    <context:component-scan base-package="com.zjw"/>

    <!--配置spring开启注解AOP的支持-->
    <aop:aspectj-autoproxy/>

</beans>
```

Service层
```java
package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 * @author zjw
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Override
    public void saveAccount() {
        System.out.println("saveAccount().....");
        int i = 1/0;
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    @Override
    public int deleteAccount() {
        System.out.println("deleteAccount()......");
        return 0;
    }
}
```

切面类
```java
package com.zjw.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 * Aspect注解 表示当前类是一个切面类
 * @author zjw
 */
@Component("logger")
@Aspect
public class Logger {

    @Pointcut("execution(* com.zjw.service.impl.*.*(..))")
    private void pt1(){}

    /**
     * 前置通知
     */
//    @Before("pt1()")
    public void beforePrintLog(){
        System.out.println("前置通知..........执行方法之前");
    }

    /**
     * 后置通知
     */
//    @AfterReturning("pt1()")
    public void afterReturningPrintLog(){
        System.out.println("后置通知..........执行方法结束");
    }

    /**
     * 异常通知
     */
//    @AfterThrowing("pt1()")
    public void afterThrowingPrintLog(){
        System.out.println("异常通知..........相当于cache里面的内容");
    }
    /**
     * 最终通知
     */
//    @After("pt1()")
    public void afterPrintLog(){
        System.out.println("最终通知..........相当于finally");
    }

    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有。
     * 解决：
     *      Spring框架为我们提供了一个接口，ProceedingJoinPoint。该接口有一个方法proceed(),此方法就相当于明确调用切入点方法。
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。
     *
     */
    @Around("pt1()")
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            System.out.println("..........前置通知");
            //得到方法执行所需的参数
            Object[] args = pjp.getArgs();
            rtValue = pjp.proceed(args);
            System.out.println("..........后置通知");
            return rtValue;
        }catch (Throwable t){
            System.out.println("..........异常通知");
            throw new RuntimeException(t);
        }finally {
            System.out.println("..........最终通知");
        }
    }



}
```

## 测试
```java
package com.zjw.test;

import com.zjw.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试AOP的配置
 */
public class AOPTest {
    public static void main(String[] args) {
        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        IAccountService accountService = (IAccountService)ac.getBean("accountService");
        //3、执行方法
        accountService.saveAccount();
    }
}
```
结果
```
..........前置通知
saveAccount().....
..........异常通知
..........最终通知
Exception in thread "main" java.lang.RuntimeException: java.lang.ArithmeticException: / by zero
```