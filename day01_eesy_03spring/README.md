# Spring容器管理对象

## 依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day01_eesy_03spring</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <!--解决pom变更后Language level、target bytecode version变更-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <encoding>UTF-8</encoding>
        <spring.version>6.1.1</spring.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
    </dependencies>

</project>
```

添加Spring配置文件

> bean.xml放在resources目录下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 把对象的创建交给spring来管理，scope=="prototype"表示多例 -->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl" scope="prototype"/>
    <bean id="accountDao" class="com.zjw.dao.impl.AccountDaoImpl"/>

</beans>
```

# 测试
```java
/**
 * 模拟一个表现层，用于调用业务层
 * @author zjw
 */
public class Client {

    /**
     * 获取spring的IOC核心容器，并根据id获取对象
     * ApplicationContext的三个常用实现类：
     *      ClassPathXmlApplicationContext:它可以加载类路径下的配置文件，要求文件必须在类路径下。不在的话，加载不了。（更常用）
     *      FileSystemXmlApplicationContext:它可以加载磁盘任意路径下的配置文件（必须有访问权限）
     *
     *      AnnotationConfigApplicationContext:它是用于读取注解创建容器的，是明天的内容
     *
     *  核心容器的两个接口引发出的问题：
     *  ApplicationContext: 单例对象适用 采用此接口
     *      它在构建核心容器时，创建对象采取的策略是单例对象立即加载的方式。也就是说，只要一读取往配置文件马上就创建配置为文件中的对象。
     *
     *  BeanFactory: 多例对象适用
     *      它在构建核心容器时，创建对象采取的策略时延迟加载的方式。也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。
     */
    public static void main(String[] args) {

//        IAccountService accountService = new AccountServiceImpl();
//        accountService.saveAccount();

        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//        ApplicationContext ac = new FileSystemXmlApplicationContext("D:/系统文件夹/桌面/bean.xml");

        //2.根据id获取Bean对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService");
        System.out.println(accountService);
        System.out.println(accountService2);

        //accountDao是单例模式，两个对象一样
        AccountDaoImpl accountDao = ac.getBean("accountDao", AccountDaoImpl.class);
        AccountDaoImpl accountDao2 = ac.getBean("accountDao", AccountDaoImpl.class);
        System.out.println(accountDao);
        System.out.println(accountDao2);

    }
}
```