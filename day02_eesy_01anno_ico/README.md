# 基于注解的IOC配置

曾经XML的配置
```xml
<bean id="accountService" class="com.zjw.service.impl.AccountServiceImpl"
scope="" init-method="" destroy-method="">
    <property name="" value="" | ref=""></property>
</bean>
```

注解分为用于创建对象，用于注入数据，和改变作用范围

## 1、用于创建对象的注解
他们的作用就和在xml配置文件中的<bean>标签实现的功能是一样的

 `@Component`:

**作用**：用于把当前类对象存入spring容器中

**属性**：
- value:用于指定bean的id。当我们不写时，它的默认值是当前类名，且首字母小写。

 `@Controller` :一般用在表现层
 `@Service`    :一般用在业务层
 `@Repository` :一般用在持久层

 以上三个注解他们的作用和属性与Component是一模一样

 他们三个是spring框架为我们提供明确的三层使用的注解，使我们的三层对象更加清晰

## 2、用于注入数据的

> 他们的作用就和在xml配置文件中的bean标签中写一个property标签的作用是一样的

`@Autowired`:
**作用**：自动按照类型注入。只要容器中有唯一的bean对象类型和要注入的变量类型匹配，就可以注入成功.
- 如果ICO容器中没有任何bean的类型和要注入的变量类型匹配，则报错。
- 如果有两个以上类型，按照变量名称注入。
- 如果查询的结果为空，那么会抛出异常。解决方法时，使用required=false

**出现位置**：
- 可以是变量上，也可以是方法上

**细节**：
> 在使用注解注入时，set方法就不是必须的。
> 说明：spring 4.0开始就不推荐使用属性注入，改为推荐构造器注入和setter注入
https://zhuanlan.zhihu.com/p/92395282
https://www.cnblogs.com/lvdeyinBlog/p/15178226.html

`@Qualifier`

**作用**：在按照类型注入的基础之上再按照名称注入。它在给类成员注入时不能单独使用，配合`@Autowired`使用。但是在给方法参数注入可以单独使用

**属性**：
`value`：用于指定注入bean的id

`@Resource`

**作用**：直接按照bean的id注入。它可以单独使用

- 如果没有指定name属性，当注解写在字段上时，默认取属性名进行按照名称查找，如果按照属性名称没有找到则按照属性类型查找。

- 如果指定name，就只会按照名称进行装配。

- 如果注解写在setter方法上默认取属性名进行装配。

**属性**：
- `name`:用于指定bean的id

> 以上三个注入都是只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现。
> 另外，集合类型的注入只能通过XML来实现。

`@Value`

**作用**：用于注入基本类型和String类型的数据

**属性**：

`value`:用于指定数据的值。它可以使用spring中SpEL(也就是Spring的el表达式)

> SpEL的写法：${表达式}

## 3、用于改变作用范围的
> 他们的作用就和在bean标签中使用scope属性实现的功能是一样的

`@Scope`

**作用**：用于指定bean的作用范围

**属性**：

`value`:指定范围的取值。常用取值：singleton、prototype,默认singleton

## 和生命周期相关

他们的作用就和在bean标签中使用init-method和destroy-method的作用是一样的

`@PreDestroy`
**作用**：用于指定销毁方法

`@PostConstruct`
**作用**：用于指定初始化方法

**示例**

```java
package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.service.IAccountService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 账户的业务层实现类
 */
@Component("accountService")
@Scope(value = "singleton") //默认也是单例
public class AccountServiceImpl implements IAccountService {

//    @Autowired
//    private IAccountDao accountDao ;

    //通过构造器注入
//    private final IAccountDao accountDao ;

//    @Autowired
//    @Qualifier("accountDao1")
//    private IAccountDao accountDao ;

    @Resource
//    @Resource(name = "accountDao1")
    private IAccountDao accountDao ;


    @Value("张三")
    private String name ;


    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }

    @PostConstruct
    public void init(){
        System.out.println("AccountServiceImpl……init方法执行了");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("AccountServiceImpl……destroy方法执行了");
    }

    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl构造方法。。。。");
//        this.accountDao = accountDao;
    }

//    @Autowired //此注解可以省略
//    public AccountServiceImpl(IAccountDao accountDao) {
//        System.out.println("AccountServiceImpl构造方法。。。。");
//        this.accountDao = accountDao;
//    }
}
```