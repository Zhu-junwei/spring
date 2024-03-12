# Spring基于注解的CRUD,完全没有XML

[源码](https://github.com/Zhu-junwei/spring/tree/master/day02_eesy_04account_annoioc_withoutxml)

## 代码实现

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day02_eesy_04account_annoioc_withoutxml</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <encoding>UTF-8</encoding>
        <spring.version>6.1.4</spring.version>
        <jakarta.version>2.1.0</jakarta.version>
        <lombok.version>1.18.30</lombok.version>
        <mysql.version>8.0.33</mysql.version>
        <dbutils.version>1.7</dbutils.version>
        <c3p0.version>0.9.1.2</c3p0.version>
        <junit.version>4.13.2</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>jakarta.annotation</groupId>
            <artifactId>jakarta.annotation-api</artifactId>
            <version>${jakarta.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>${dbutils.version}</version>
        </dependency>
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>${c3p0.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```
数据库配置文件
jdbcConfig.properties
```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=123456
```

数据库配置类
```java
package com.zjw.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * @author zjw
 */
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    /**
     * 用于创建一个QueryRunner对象
     * @param dataSource 数据源对象
     * @return QueryRunner对象
     */
    @Bean(name = "runner")
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(@Qualifier("dataSource") DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    /**
     * 创建数据源对象
     */
    @Bean("dataSource")
    public DataSource createDataSource(){
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
```
Spring配置类
```java
package com.zjw.config;


import org.springframework.context.annotation.*;

/**
 * 该类是一个配置类，他的作用和bean.xml是一样的
 * spring中的新注解
 * @author zjw
 * @Configuration
 *      作用：指定当前类是一个配置类
 *      细节：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以不写
 * @ComponentScan
 *      作用：用于通过注解指定spring在创建容器时要扫描的包
 *      属性：
 *          value:它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包。
 *                我们使用此注解就等同于在xml中配置了：
 *                   <context:component-scan base-package="com.zjw"></context:component-scan>
 * @Bean
 *      作用：用于把当前方法的返回值作为bean对象存入spring的ioc容器中
 *      属性：
 *          name:用于指定bean的id。当不写时，默认是当前方法的名称
 *      细节：
 *          当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象。
 *          查找的方式和Autowired注解的作用是一样的
 * @Import
 *      作用：用于导入其他配置类的字节码
 *      属性：
 *          value:用于指定其他配置类的字节码
 *                  当我们使用Import的注解之后，有Import注解的类就是父配置类，而导入的都是子配置类
 * @PropertySource
 *      作用：用于加载 .properties 文件中的配置 。例如我们配置数据源时，可以把连接数据库的信息写到
 *              properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。
 *
 *      属性：
 *          value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:
 *
 */
//@Configuration
@ComponentScan({"com.zjw"})
@Import({JdbcConfig.class})
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {

}
```

## 测试

```java
package com.zjw.test;

import com.zjw.config.SpringConfiguration;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit单元测试，测试我们的配置
 * Spring整合junit的配置 不用自己实例化容器
 *      1、导入spring整合junit的jar(坐标) spring-test
 *      2、使用Junit提供的一个注解把原有的main方法替换了，替换成spring提供的
 *          @RunWith
 *      3、告知spring的运行器，spring和ioc创建是基于xml还是注解的，并说明位置
 *          @ContextConfiguration
 *              locations:指定xml文件的位置，加上classpath关键字，表示类路径下
 *              classes:指定注解类所在的位置
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest2 {

    @Resource
    private IAccountService accountService;

    @Test
    public void testFindAll(){
        List<Account> accountList = accountService.findAllAccount();
        accountService.findAccountById(1);
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindAccountById(){
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSaveAccount(){
        Account account = new Account();
        account.setName("zaa");
        account.setMoney(9999f);
        accountService.saveAccount(account);
    }

    @Test
    public void testUpdateAccount(){
        Account account = accountService.findAccountById(4);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    @Test
    public void testDeleteAccount(){

        accountService.deleteAccount(4);

    }
}
```