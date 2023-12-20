[toc]

# Spring中的依赖注入DI

> 依赖注入的简单理解就是给对象设置变量值。

Spring配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--Spring中的依赖注入DI
            依赖注入：
                Dependency Injection
            IOC的作用：
                降低程序间的耦合（依赖关系）
            依赖关系的管理：
                以后都交给spring来维护
            在当前类需要用到其他类的对象，由spring为我们提供，我们只需要在配置文件中说明
            依赖注入的维护：
                就称为依赖注入。
              依赖注入：
                能注入的数据：有三类
                    基本类型和String
                    其他bean类型（在配置文件中或者注解配置过的bean）
                    复杂类型/集合类型
                注入的方式：有三种
                    第一种：使用构造函数提供
                    第二种：使用set方法提供
                    第三种：使用注解提供（明天的内容）

    -->

    <!--方式一 构造函数注入：
        使用的标签：constructor-arg
        标签出现的位置：bean标签的内部
        标签中的属性：
            type:用于指定要注入的数据类型，该数据类型也是构造函数中某个或某些参数的类型
            index:用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
            name:用于指定给构造函数中指定名称中的参数赋值 ***常用***
            =============以上三个用于指定给构造函数中哪个参数赋值==================
            value:用于提供基本类型和String类型的数据
            ref:用于指定其他的bean类型数据。它指的几句诗在spring的Ioc核心容器中出现过的bean对象

        优势：在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功
        弊端：改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
    -->
    <bean id="accountService1" class="com.zjw.service.impl.AccountServiceImpl">
        <!--<constructor-arg name="name" value="zjw"/>-->
        <constructor-arg index="0" value="aaa"/>
        <constructor-arg name="age" value="25"/>
        <constructor-arg name="birthday" ref="now"/>
    </bean>

    <!--配置一个时间对象-->
    <bean id="now" class="java.util.Date"/>

    <!--方式二 set方法注入 更常用的方式
        涉及的标签：property
        出现的位置：bean标签的内部
        标签的属性
            name:用于指定给set方法名称
            value:用于提供基本类型和String类型的数据
            ref:用于指定其他的bean类型数据。它指的几句诗在spring的Ioc核心容器中出现过的bean对象
        优势：
            创建对象时没有明确的限制，可以直接使用默认构造函数
        弊端：
            如果有某些成员必须有值，则获取对象是有可能set方法没有执行
    -->
    <bean id="accountService2" class="com.zjw.service.impl.AccountService2Impl">
        <property name="age" value="18"/>
        <property name="name" value="zjw"/>
        <property name="birthday" ref="now"/>
    </bean>

    <!--复杂类型的注入/集合类型的注入
        用于给List结构集合注入的标签
            array,list,set
        用于给Map结构集合注入的标签
            map props
        结构相同，标签可以互换
    -->
    <bean id="accountService3" class="com.zjw.service.impl.AccountService3Impl">
        <!--给数组[]赋值-->
        <property name="myStrs">
            <set>
                <value>aaa</value>
                <value>bbb</value>
                <value>ccc</value>
            </set>
        </property>
        <!--List集合赋值-->
        <property name="myList">
            <set>
                <value>list1</value>
                <value>bbb</value>
                <value>ccc</value>
            </set>
        </property>
        <!--Set集合赋值-->
        <property name="mySet">
            <set>
                <value>set1</value>
                <value>bbb</value>
                <value>ccc</value>
            </set>
        </property>
        <!--Map集合赋值-->
        <property name="myMap">
            <map>
                <entry key="testA" value="aaa"/>
                <entry key="testB" value="BBB"/>
            </map>
        </property>
        <!--Properties集合赋值-->
        <property name="myProps">
            <props>
                <prop key="testC">CC</prop>
                <prop key="testD">DD</prop>
            </props>
        </property>

    </bean>

</beans>
```

AccountServiceImpl
```java
package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.ToString;

import java.util.Date;

/**
 * 账户的业务层实现类
 * @author zjw
 */
@ToString
public class AccountServiceImpl implements IAccountService {

    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public AccountServiceImpl(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceImpl……中的saveAccount方法执行了\n"+this.toString()+"\n");
    }

}
```

AccountService2Impl
```java
/**
 * 账户的业务层实现类
 * @author zjw
 */
@Setter
@ToString
public class AccountService2Impl implements IAccountService {

    private String name;
    private Integer age;
    private Date birthday;

    @Override
    public void saveAccount() {
        System.out.println("AccountService2Impl……中的saveAccount方法执行了\n"+ this + "\n");
    }
    
}
```

AccountService3Impl
```java
/**
 * 账户的业务层实现类
 * @author zjw
 */
@Setter
public class AccountService3Impl implements IAccountService {

    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;
    
    @Override
    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了");
        System.out.println("数组myStrs: " + Arrays.toString(myStrs));
        System.out.println("List集合myList：" + myList);
        System.out.println("Set集合：" + mySet);
        System.out.println("Map集合：" + myMap);
        System.out.println("Properties：" + myProps);
    }
}
```

测试
```java
/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {

        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        System.out.println("*****accountService1……通过构造方法依赖注入*****");
        IAccountService accountService1 = (IAccountService) ac.getBean("accountService1");
        accountService1.saveAccount();

        System.out.println("*****accountService2……通过setter方法依赖注入*****");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService2");
        accountService2.saveAccount();

        System.out.println("*****accountService3……复杂类型的注入/集合类型的注入*****");
        IAccountService accountService3 = (IAccountService) ac.getBean("accountService3");
        accountService3.saveAccount();
    }
}
```
结果
```
*****accountService1……通过构造方法依赖注入*****
AccountServiceImpl……中的saveAccount方法执行了
AccountServiceImpl(name=aaa, age=25, birthday=Wed Dec 20 20:54:53 CST 2023)

*****accountService2……通过setter方法依赖注入*****
AccountService2Impl……中的saveAccount方法执行了
AccountService2Impl(name=zjw, age=18, birthday=Wed Dec 20 20:54:53 CST 2023)

*****accountService3……复杂类型的注入/集合类型的注入*****
accountService中的saveAccount方法执行了
数组myStrs: [aaa, bbb, ccc]
List集合myList：[list1, bbb, ccc]
Set集合：[set1, bbb, ccc]
Map集合：{testA=aaa, testB=BBB}
Properties：{testD=DD, testC=CC}
```