[toc]

# Spring编程式事务控制

> 实际中很少使用

## 代码实现

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day04_eesy_08account_tx</artifactId>
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
        <mysql.version>8.0.33</mysql.version>
        <junit.version>4.13.2</junit.version>
        <lombok.version>1.18.30</lombok.version>
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
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置业务层-->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="transactionTemplate" ref="transactionTemplate"/>
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

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务模板对象-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
</beans>
```
Service层
```java
package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import lombok.Setter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 账户的业务层实现类
 * <p>
 * 事务的控制应该都在业务层
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Setter
    private IAccountDao accountDao;

    @Setter
    private TransactionTemplate transactionTemplate ;

    @Override
    public Account findAccountById(Integer accountId) {
        return transactionTemplate.execute(new TransactionCallback<Account>() {
            @Override
            public Account doInTransaction(TransactionStatus status) {
                return accountDao.findAccountById(accountId);
            }
        });

    }

    /**
     * 声明式事务控制，代码太不优雅
     */
    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
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
                return null;
            }
        });


    }
}

```

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
        as.transfer("aaa", "bbb", 100f);
    }
}
```