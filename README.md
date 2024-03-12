# Spring 学习笔记

## 版本说明

| 依赖     | 版本     |
|--------|--------|
| Spring | 6.1.4  |
| MySQL  | 8.0.33 |
| JDK    | 17.0.5 |

## 运行

> 执行module中的main方法或test测试方法

运行module前取消IDEA中`Delegate IDE build/rum actions to Maven`配置

![20210621_232923](img/20210621_232923.png)

## 模块

| 模块                                      | 说明                                                                   |
|-----------------------------------------|----------------------------------------------------------------------|
| day01_eesy_01jdbc                       | [使用JDBC连接数据库](day01_eesy_01jdbc)[](day01_eesy_01jdbc)                |
| day01_eesy_02factory                    | [通过创建一个Bean对象工厂来获取对象](day01_eesy_02factory)                          |
| day01_eesy_03spring                     | [Spring容器管理对象](day01_eesy_03spring)                                  |
| day01_eesy_04bean                       | [Spring创建Bean的三种方式及Bean的生命周期](day01_eesy_04bean)                     |
| day01_eesy_05DI                         | [Spring中的依赖注入DI](day01_eesy_05DI)                                    |
| day02_eesy_01anno_ico                   | [基于注解的IOC配置](day02_eesy_01anno_ico)                                  |
| day02_eesy_02account_xmlioc             | [基于xml的CRUD](day02_eesy_02account_xmlioc)                            |
| day02_eesy_03account_annoioc            | [Spring基于注解的CRUD](day02_eesy_03account_annoioc)                      |
| day02_eesy_04account_annoioc_withoutxml | [Spring基于注解的CRUD,完全没有XML](day02_eesy_04account_annoioc_withoutxml)   |
| day03_eesy_01account                    | [Spring连接线程的事务控制](day03_eesy_01account)                              |
| day03_eesy_02proxy                      | [动态代理](day03_eesy_02proxy)                                           |
| day03_eesy_03springAOP                  | [Spring AOP面向切面编程](day03_eesy_03springAOP)                           |
| day03_eesy_04adviceType                 | [Spring AOP面向切面编程 通知类型](day03_eesy_04adviceType)                     |
| day03_eesy_05annotationAOP              | [Spring 基于注解的AOP面向切面编程](day03_eesy_05annotationAOP)                  |
| day04_eesy_01jdbctemplate               | [Spring JdbcTemplate操作数据库](day04_eesy_01jdbctemplate)                |
| day04_eesy_02account_aoptx_xml          | [Spring基于XML AOP事务控制](day04_eesy_02account_aoptx_xml)                |
| day04_eesy_03account_aoptx_anno         | [Spring基于注解的AOP事务控制](day04_eesy_03account_aoptx_anno)                |
| day04_eesy_04tx                         | [Spring 初始项目，不能解决事务问题](day04_eesy_04tx)                              |
| day04_eesy_05tx_xml                     | [Spring基于XML的事务管理器DataSourceTransactionManager](day04_eesy_05tx_xml) |
| day04_eesy_06tx_anno                    | [Spring基于注解的事务管理](day04_eesy_06tx_anno)                              |
| day04_eesy_07anno_tx_withoutxml         | [Spring纯注解的事务管理](day04_eesy_07anno_tx_withoutxml)                    |
| day04_eesy_08account_tx                 | [Spring编程式事务控制](day04_eesy_08account_tx)                             |

