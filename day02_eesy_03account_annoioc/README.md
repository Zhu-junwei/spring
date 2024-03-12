[toc]

# Spring基于注解的CRUD

[源码](https://github.com/Zhu-junwei/spring/tree/master/day02_eesy_03account_annoioc)

## 代码实现

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day02_eesy_03account_annoioc</artifactId>
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
        <mysql.version>8.0.33</mysql.version>
        <dbutils.version>1.7</dbutils.version>
        <c3p0.version>0.9.1.2</c3p0.version>
        <lombok.version>1.18.30</lombok.version>
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

Dao层实现
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 * @author zjw
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    public QueryRunner runner;

    /**
     * 使用Lookup注解，告诉Spring这个方法需要使用查找方法注入
     * 这里直接使用@Lookup，则Spring将会依据方法返回值
     * 将它覆盖为一个在Spring容器中获取QueryRunner这个类型的bean的方法
     * 但是也可以指定需要获取的bean的名字，如：@Lookup("runner")
     * 此时，名字为runner的bean，类型必须与方法的返回值类型一致
     */
    @Lookup
    public QueryRunner getRunner(){
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

> Java8后Resource不在javax中维护，改为了jakarta

```java
package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户的业务层实现类
 * @author zjw
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Resource
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

### 方式一：使用Junit方式测试

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

    private  ApplicationContext ac ;

    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("bean.xml");
    }

    /**
     * 查询所有账户
     */
    @Test
    public void testFindAll(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        List<Account> accountList = accountService.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    /**
     * 根据账户id查询账户
     */
    @Test
    public void testFindAccountById(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    /**
     * 新建账户
     */
    @Test
    public void testSaveAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = new Account();
        account.setId(4);
        account.setName("zzz");
        account.setMoney(9999f);
        accountService.saveAccount(account);
    }

    /**
     * 更新账户信息
     */
    @Test
    public void testUpdateAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        Account account = accountService.findAccountById(4);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    /**
     * 删除账户
     */
    @Test
    public void testDeleteAccount(){
        IAccountService accountService = ac.getBean("accountService", IAccountService.class);
        accountService.deleteAccount(4);
    }
}
```

### 方式二：使用`@RunWith(SpringJUnit4ClassRunner.class)`注解测试

```java
package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用@RunWith测试，测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/*.xml"})
public class AccountServiceTest2 {

    @Resource
    private IAccountService accountService;

    /**
     * 查询所有账户
     */
    @Test
    public void testFindAll(){
        List<Account> accountList = accountService.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    /**
     * 根据账户id查询账户
     */
    @Test
    public void testFindAccountById(){
        Account account = accountService.findAccountById(1);
        System.out.println(account);
    }

    /**
     * 新建账户
     */
    @Test
    public void testSaveAccount(){
        Account account = new Account();
        account.setId(4);
        account.setName("zzz");
        account.setMoney(9999f);
        accountService.saveAccount(account);
    }

    /**
     * 更新账户信息
     */
    @Test
    public void testUpdateAccount(){
        Account account = accountService.findAccountById(4);
        System.out.println(account);
        account.setMoney(888F);
        accountService.updateAccount(account);
    }

    /**
     * 删除账户
     */
    @Test
    public void testDeleteAccount(){
        accountService.deleteAccount(4);
    }
}
```