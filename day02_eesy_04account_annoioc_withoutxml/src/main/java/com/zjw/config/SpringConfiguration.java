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
