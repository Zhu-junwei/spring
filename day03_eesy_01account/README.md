# Spring连接线程的事务控制

> 通过把线程`ThreadLocal`绑定数据库来连接`Connection`来控制事务

[源码](https://github.com/Zhu-junwei/spring/tree/master/day03_eesy_01account)

**实现的方式不够优雅**


## 代码实现

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zjw</groupId>
    <artifactId>day03_eesy_01account</artifactId>
    <version>1.0-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <encoding>UTF-8</encoding>
        <spring.version>6.1.4</spring.version>
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
        </dependency>
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>${dbutils.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
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

Spring配置文件
bean.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置代理的service-->
    <bean id="proxyAccountService" factory-bean="beanFactory" factory-method="getAccountService"/>

    <!--配置beanFactory-->
    <bean id="beanFactory" class="com.zjw.factory.BeanFactory">
        <!--注入service-->
        <property name="accountService" ref="accountService"/>
        <!--注入事务管理器-->
        <property name="txManager" ref="txManager"/>
    </bean>

    <!-- 配置Service -->
    <bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl">
        <!-- 注入dao -->
        <property name="accountDao" ref="accountDao"/>
    </bean>
    <bean id="accountService_old" class="com.zjw.service.impl.AccountServiceImpl_OLD">
        <!-- 注入dao ,事务管理 -->
        <property name="accountDao" ref="accountDao"/>
        <property name="txManager" ref="txManager"/>
    </bean>

    <!--配置Dao对象-->
    <bean id="accountDao" class="com.zjw.dao.impl.AccountDaoImpl">
        <!-- 注入QueryRunner -->
        <property name="runner" ref="runner"/>
        <property name="connectionUtils" ref="connectionUtils"/>
    </bean>


    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype"/>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/eesy_spring?useSSL=false&amp;serverTimeZone=Asia/Shanghai"/>
        <property name="user" value="root"/>
        <property name="password" value="123456"/>
    </bean>

    <!--配置Connection的工具类 ConnectionUtils-->
    <bean id="connectionUtils" class="com.zjw.utils.ConnectionUtils">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="txManager" class="com.zjw.utils.TransactionManager">
        <property name="connectionUtils" ref="connectionUtils"/>
    </bean>
</beans>
```

连接工具类`ConnectionUtils`
```java
package com.zjw.utils;

import lombok.Setter;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 * @author zjw
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    @Setter
    private DataSource dataSource;

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection(){
        try {
            //1、先从ThreadLocal上获取
            Connection conn = tl.get();
            //2、判断当前线程上是否有连接
            if (conn == null){
                //3、从数据源中获取一个连接，并且存入ThreadLocal中
                conn = dataSource.getConnection();
                tl.set(conn);
            }
            //4、返回当前线程上的连接
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        tl.remove();
    }
}
```

事务管理相关的工具类`TransactionManager`
```java
package com.zjw.utils;

import lombok.Setter;

/**
 * 和事务管理相关的工具类，它包含了，开启事务，提交事务，回滚事务和释放连接
 * @author zjw
 */
public class TransactionManager {

    @Setter
    private ConnectionUtils connectionUtils;

    /**
     * 开启事务
     */
    public void beginTransaction(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 提交事务
     */
    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 回滚事务
     */
    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 释放连接
     */
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();//还回连接池中
            connectionUtils.removeConnection();//
        }catch (Exception e){
            e.printStackTrace();
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
 * <p>
 * 事务的控制应该都在业务层
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Setter
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

Service的代理对象的工厂
```java
package com.zjw.factory;

import com.zjw.service.IAccountService;
import com.zjw.utils.TransactionManager;
import lombok.Setter;

import java.lang.reflect.Proxy;

/**
 * 用于创建service的代理对象的工厂
 * @author zjw
 */
public class BeanFactory {

    @Setter
    private IAccountService accountService ;

    @Setter
    private TransactionManager txManager;

    public IAccountService getAccountService(){
        return (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object rtValue ;
                    try {
                        //1、开启事务
                        txManager.beginTransaction();
                        //2、执行操作
                        rtValue = method.invoke(accountService,args);
                        //3、提交事务
                        txManager.commit();
                        //4、返回结果
                        return rtValue;
                    }catch (Exception e){
                        //5、回滚操作
                        txManager.rollback();
                        throw new RuntimeException(e);
                    }finally {
                        //6、释放连接
                        txManager.release();
                    }
                });

    }
}
```

Dao层实现
```java
package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.utils.ConnectionUtils;
import lombok.Setter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * 账户的持久层实现类
 * @author zjw
 */
public class AccountDaoImpl implements IAccountDao {

    @Setter
    private QueryRunner runner;

    @Setter
    private ConnectionUtils connectionUtils;

    @Override
    public List<Account> findAllAccount() {
        try{
            return runner.query(connectionUtils.getThreadConnection(),"select * from account",new BeanListHandler<Account>(Account.class));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {
        try{
            return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ? ",new BeanHandler<Account>(Account.class),accountId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try{
            runner.update(connectionUtils.getThreadConnection(),"insert into account(name,money)values(?,?)",account.getName(),account.getMoney());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try{
            runner.update(connectionUtils.getThreadConnection(),"update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {
        try{
            runner.update(connectionUtils.getThreadConnection(),"delete from account where id=?",accountId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        try{
            List<Account> accounts =  runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ? ", new BeanListHandler<>(Account.class),accountName);
            if (accounts==null || accounts.size()==0) {
                return null;
            }
            if (accounts.size() > 1) {
                throw new RuntimeException("结果集不唯一，数据有问题");
            }
            return accounts.get(0);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

## 测试
```java
/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
//    @Qualifier("accountService_old")
    @Qualifier("proxyAccountService")
    private IAccountService as;

    @Test
    public void testTransfer() {

        as.transfer("aaa", "bbb", 1f);

    }
}
```