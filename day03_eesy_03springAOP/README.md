# Spring AOP面向切面编程

> `AOP`：全称是`Aspect  Oriented  Programming`即：面向切面编程。

> 在运行时，动态地将代码切入到类的指定方法、指定位置上的编程思想就是面向切面的编程

参考文档

https://docs.qq.com/pdf/DTXZtQ0FFb05paUJS

[源码](https://github.com/Zhu-junwei/spring/tree/master/day03_eesy_03springAOP)

## 代码测试

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day03_eesy_03springAOP</artifactId>
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
        <aspectjweaver.version>1.9.19</aspectjweaver.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--解析接入点表达式-->
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
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring的Ioc，把service对象配置进来-->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl"/>
    <!--配置Logger类-->
    <bean id="logger" class="com.zjw.utils.Logger"/>

    <!--spring中基于xml的AOP配置步骤
        1、使用通知Bean也交给spring来管理
        2、使用aop:config标签表明开始AOP的配置
        3、使用aop:aspect标签表明配置切面
            id属性：是给切面提供一个唯一标识
            ref属性：是指定通知类bean的Id.
        4、在aop:aspect标签的内部使用对应标签来配置通知的类型
            我们现在示例是让printLog方法在切入点方法执行之前：所以是前置通知
            aop:before :表示配置前置通知
                method属性：用于指定Logger类中哪个方法是前置通知
                pointcut属性：用于指定切入点表达式，该表达式的含义指的是对业务层中哪些方法增强

            切入点表达式的写法：
                关键字：execution(表达式)
                表达式：
                    访问修饰符 返回值 包名.包名.包名...类.方法名（参数列表）
                标准的表达式写法：
                    public void com.zjw.service.impl.AccountServiceImpl.saveAccount()
                访问修饰符可以省略：
                    void com.zjw.service.impl.AccountServiceImpl.saveAccount()
                返回值可以使用通配符表示任意通配符：
                    * com.zjw.service.impl.AccountServiceImpl.saveAccount()
                包名可以使用通配符，表示任意包。但是有几级包，就需要写几个*
                    * *.*.*.*.AccountServiceImpl.saveAccount()
                包名可以使用..表示当前包及其子包
                    * *..AccountServiceImpl.saveAccount()
                类名和方法名都可以使用*来实现统配
                    * *..*.*()
                参数列表：
                    可以直接写数据类型：
                        基本类型直接写名称： int
                        引用类型写包名.类名的方式 java.lang.String
                     可以使用通配符表示任意类型，但必须要有参数
                     可以使用..表示有无参数均可，有参数可以是任意类型
                全通配写法：
                    * *..*.*(..)
                在实际开发中切入点表达式的通常写法：
                    切到业务层实现类下的所有方法
                     * com.zjw.service.impl.*.*(..)
    -->
    <!--配置AOP-->
    <aop:config>
        <!--配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
            <!--配置通知的类型，并且建立通知方法和切入点方法的关联-->
            <aop:before method="printLog" pointcut="execution(* com.zjw.service.impl.*.*(..))"/>
        </aop:aspect>
    </aop:config>

</beans>
```
Service层实现
```java
package com.zjw.service.impl;

import com.zjw.service.IAccountService;

/**
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Override
    public void saveAccount() {
        System.out.println("saveAccount().....");
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    @Override
    public void deleteAccount() {
        System.out.println("deleteAccount()......");
    }
}
```

定义切面
```java
package com.zjw.utils;

/**
 * 作为切面
 * 用于记录日志的工具类，它里面提供了公共的代码
 * @author zjw
 */
public class Logger {

    /**
     * 用于打印日志：计划让其再切入点方法执行之前执行（切入点方法就是业务层方法）
     */
    public void printLog(){
        System.out.println("...........printLog().........");
    }
}
```

## 测试类
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
        accountService.updateAccount(1);
        accountService.deleteAccount();
    }
}
```
结果
```
...........printLog().........
saveAccount().....
...........printLog().........
updateAccount(int i)....1
...........printLog().........
deleteAccount()......
```