# Spring JdbcTemplate操作数据库

[源码](https://github.com/Zhu-junwei/spring/tree/master/day04_eesy_01jdbctemplate)

## 代码测试

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

第一种：注入JdbcTemplate对象操作数据库
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjw
 */
@Repository
public class AccountDaoImpl implements IAccountDao {

    @Setter
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Integer accountId) {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE id=?", new BeanPropertyRowMapper<>(Account.class),accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE name=?", new BeanPropertyRowMapper<>(Account.class),accountName);
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
        jdbcTemplate.update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
    }
}
```
第二种：通过注入DateSource对象创建JdbcTemplate

```java
package com.zjw.dao.impl;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zjw
 */
public class JdbcDaoSupport {

    @Getter
    private JdbcTemplate jdbcTemplate ;

    public void setDataSource(DataSource dataSource){
        if (jdbcTemplate == null){
            jdbcTemplate = createJdbcTemplate(dataSource);
        }
    }

    private JdbcTemplate createJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
```
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @author zjw
 */
public class AccountDaoImpl2 extends JdbcDaoSupport implements IAccountDao {

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

## 测试使用
直接在代码中创建JdbcTemplate对象
```java
package com.zjw.jdbctemplate;


import com.zjw.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

/**
 * @author zjw
 */
public class JdbcTemplateDem01 {
    public static void main(String[] args) {
        //准备数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&serverTimeZone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        //1、创建JdbcTemplate对象
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        //2、执行操作
        //插入
//        jt.execute("INSERT INTO account(name,money) values ('zbbb',1000)");
        //查询
        List<Account> accounts = jt.query("SELECT * FROM account ", new BeanPropertyRowMapper<Account>(Account.class));
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
```
获取容器中的JdbcTemplate对象
```java
package com.zjw.jdbctemplate;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcTemplate的最基本用法
 * @author zjw
 */
public class JdbcTemplateDem02 {
    public static void main(String[] args) {

        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        JdbcTemplate jdbcTemplate = ac.getBean("jdbcTemplate", JdbcTemplate.class);
        //3、执行操作
        jdbcTemplate.execute("INSERT INTO account(name,money) values ('zccc',1000)");
    }
}
```

JdbcTemplate的CRUD操作
```java
package com.zjw.jdbctemplate;


import com.zjw.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JdbcTemplate的CRUD操作
 *
 * @author zjw
 */
public class JdbcTemplateDem03 {
    public static void main(String[] args) {

        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        JdbcTemplate jdbcTemplate = ac.getBean("jdbcTemplate", JdbcTemplate.class);
        //3、执行操作
//        jdbcTemplate.execute("INSERT INTO account(name,money) values ('zccc',1000)");
        //保存
//        jdbcTemplate.update("INSERT INTO account(name,money)values(?,?)","zeee",333F);
        //更新
//        jdbcTemplate.update("UPDATE account SET name=?,money=? WHERE id =? ","zeee",9999F,9);
        //删除
//        jdbcTemplate.update("DELETE FROM account WHERE id =? ",8);
        //查询所有
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE money>?", new AccountRowMapper(), 1000f);
        //Spring 提供的封装BeanPropertyRowMapper
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE money > ?", new BeanPropertyRowMapper<>(Account.class), 1000f);
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account", new BeanPropertyRowMapper<>(Account.class));
//        for (Account account : accounts){
//            System.out.println(account);
//        }

        //查询一个
//        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE id=?", new BeanPropertyRowMapper<>(Account.class), 1);
//        System.out.println(accounts.isEmpty()?"没有内容":accounts.get(0));
        //查询返回一行一列（使用聚合函数，但不加group by子句）
//        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM account WHERE money>?", Long.class, 1000f);
//        System.out.println(count);
    }
}
class AccountRowMapper implements RowMapper<Account>{

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setMoney(resultSet.getFloat("money"));
        return account;
    }
}
```

调用Dao层操作数据库
```java
package com.zjw.jdbctemplate;


import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JdbcTemplate的最基本用法
 * @author zjw
 */
public class JdbcTemplateDem04 {
    public static void main(String[] args) {
        //1、获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2、获取对象
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);
//        IAccountDao accountDao = ac.getBean("accountDao2", IAccountDao.class);
        Account account = accountDao.findAccountById(1);
        System.out.println(account);
//        account.setMoney(3000F);
//        accountDao.updateAccount(account);
    }
}
```

