[toc]

# 基于xml的CRUD

[源码](https://github.com/Zhu-junwei/spring/tree/master/day02_eesy_02account_xmlioc)

- 使用C3P0连接池
- 使用dbutils包中的QueryRunner类来对数据库进行操作

## 代码实现

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day02_eesy_02account_xmlioc</artifactId>
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

bean.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置Service-->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl">
        <!--注入dao-->
        <property name="accountDao" ref="accountDao"/>
    </bean>

    <!--配置Dao对象-->
    <bean id="accountDao" class="com.zjw.dao.impl.AccountDaoImpl">
        <!--使用spring 方法注入创建多例的runner Spring应用了CGLIB（动态代理）-->
        <lookup-method name="getRunner" bean="runner"/>
    </bean>

    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg name="ds" ref="dataSource"/>
    </bean>

    <!--配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&amp;serverTimeZone=Asia\Shanghai"/>
        <property name="user" value="root"/>
        <property name="password" value="123456"/>
    </bean>

</beans>
```

Dao层实现
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 */
//@Getter
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;

    // 虽然有get方法，但是并不会执行，而是由容器提供一个动态代理的实现
    public QueryRunner getRunner() {
        System.out.println("getRunner方法执行....");
        return runner;
    }

    @Override
    public List<Account> findAllAccount() {
        try {
            return getRunner().query("SELECT * FROM account",new BeanListHandler<>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {
        try {
            return getRunner().query("SELECT * FROM account WHERE id=?",new BeanHandler<>(Account.class),accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            getRunner().update("INSERT INTO account(name,money) VALUES(?,?)",account.getName(),account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            getRunner().update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {
        try {
            getRunner().update("DELETE FROM account WHERE id=?",accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

Service层实现

```java
package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import lombok.Setter;

import java.util.List;

/**
 * 账户的业务层实现类
 * @author zjw
 */
@Setter
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountDao.deleteAccount(accountId);
    }
}
```

## 测试
```java
package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 使用Junit单元测试，测试我们的配置
 */
public class AccountServiceTest {

    private ApplicationContext ac ;

    /**
     * 1. 初始化容器
     */
    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("bean.xml");
    }

    @Test
    public void testFindAll(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        List<Account> accountList = accountService.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindAccountById(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);

    }

    @Test
    public void testSaveAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = new Account();
        account.setId(7);
        account.setName("abc");
        account.setMoney(9999f);
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdateAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    @Test
    public void testDeleteAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        accountService.deleteAccount(7);
    }
}
```