# Spring基于XML的事务管理器DataSourceTransactionManager

[源码](https://github.com/Zhu-junwei/spring/tree/master/day04_eesy_04tx_xml)

## 代码测试

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day04_eesy_05tx_xml</artifactId>
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
        <mysql.version>8.0.33</mysql.version>
        <dbutils.version>1.7</dbutils.version>
        <c3p0.version>0.9.1.2</c3p0.version>
        <lombok.version>1.18.30</lombok.version>
        <junit.version>4.13.2</junit.version>
        <aspectjweaver.version>1.9.21</aspectjweaver.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectjweaver.version}</version>
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

Spring配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">


    <!--配置业务层-->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"/>
    </bean>
    <!--配置账户的持久层-->
    <bean id="accountDao" class="com.zjw.dao.impl.AccountDaoImpl">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&amp;serverTimeZone=Asia/Shanghai"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>

    <!--spring中基于XML的声明式事务控制配置步骤
        1、配置事务管理器
        2、配置事务的通知
            此时我们需要导入事务的约束 tx名称空间和约束，同时也需要aop的
            使用tx:advice标签配置事务通知
                属性：
                    id:给事务通知起一个唯一标识
                    transaction-manage:给事务通知提供一个事务管理器引用
        3、配置AOP中通用切入点表达式
        4、建立事务通知和切入点表达式的对应关系
        5、配置事务的属性
            是在事务的通知tx:advice标签的内部
    -->
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--配置事务的通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!--配置事务的属性
                isolation：用于指定事务的隔离级别。默认值时DEFAULT，表示使用数据库的默认隔离级别。
                propagation：用于指定事务的传播行为。
                            REQUIRED,默认值，表示一定会有事务，增删改的选择。
                            SUPPORTS 如果当前环境有事务，就加入到当前事务；如果没有事务，就以非事务的方式执行。查询方法可以选择SUPPORTS.
                read-only：用于指定事务是否只读。只用查询方法才能设置为true.默认值为false,表示读写。
                timeout：用于指定事务的超时时间，默认值是-1,表示永不超时。如果指定了数值，以秒为单位。
                rollback-for：用于指定一个异常，当产生该异常时，事务回滚，产生其他异常时，事务不回滚。没有默认值。表示任何异常都回滚。
                no-rollback-for：用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常时，事务回滚。没有默认值。表示任何异常都回滚。
            -->
            <!--使用数据库的隔离级别，一定有事务，事务读写，事务永不超时，任何异常都回滚-->
            <tx:method name="*"/>
            <!--使用数据库的隔离级别，一定有事务，事务读写，事务永不超时，任何异常都回滚-->
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>
    <!--配置AOP-->
    <aop:config>
        <!--配置切入点表达式-->
        <aop:pointcut id="pt1" expression="execution(* com.zjw.service.impl.*.*(..))" />
        <!--建立事务通知和切入点表达式的对应关系-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"/>
    </aop:config>

</beans>
```
Service层
```java
package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import lombok.Setter;

/**
 * 账户的业务层实现类
 * <p>
 * 事务的控制应该都在业务层
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Setter
    private IAccountDao accountDao;

    @Override
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {

        //2、执行操作
        //2.1、根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2、根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3、转出账户减钱
        source.setMoney(source.getMoney() - money);
        //2.4、转入账户加钱
        target.setMoney(target.getMoney() + money);
        //2.5、更新转出账户
        accountDao.updateAccount(source);

        int i = 1 / 0;

        //2.6、更新转入账户
        accountDao.updateAccount(target);

    }
}

```
> 基于xml的好处是对Service层是不需要改动的

Dao层
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author zjw
 */
public class AccountDaoImpl extends JdbcDaoSupport implements IAccountDao {


    @Override
    public Account findAccountById(Integer accountId) {
        List<Account> accounts = getJdbcTemplate().query("SELECT * FROM account WHERE id=?", new BeanPropertyRowMapper<Account>(Account.class),accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = getJdbcTemplate().query("SELECT * FROM account WHERE name=?", new BeanPropertyRowMapper<Account>(Account.class),accountName);
        if (accounts.isEmpty()){
            return null;
        }
        if (accounts.size()>1){
            throw new RuntimeException("结果集不唯一");
        }
        return accounts.get(0);
    }

    @Override
    public void updateAccount(Account account) {
        getJdbcTemplate().update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
    }
}

```


## 测试

```java
package com.zjw.test;

import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
    private IAccountService as;

    @Test
    public void testTransfer() {
        as.transfer("bbb", "aaa", 100f);
    }
}
```
