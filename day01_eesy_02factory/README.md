# 通过创建一个Bean对象工厂来获取对象

## BeanFactory工厂

```java
/**
 * 一个创建Bean对象的工厂
 * @author zjw
 */
public class BeanFactory {

    /**
     * 定义一个Properties对象
     */
    private static Properties properties;

    /**
     * 定义一个Map，用于存放我们要创建的对象，我们把它称为容器
     */
    private static Map<String,Object> beans;

    //使用静态代码块为Properties对象赋值
    static {
        try {
            System.out.println("初始化容器开始-----");
            //实例化对象
            properties = new Properties();
            //获取properties文件的流对象
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            properties.load(in);
            //实例化容器
            beans = new HashMap<>();
            //取出配置文件中所有的key
            Enumeration<Object> keys = properties.keys();
            //遍历枚举
            while (keys.hasMoreElements()){
                String key = keys.nextElement().toString();
                //根据key获取value
                String beanPath = properties.getProperty(key);
                //反射创建对象
                Object value = Class.forName(beanPath).newInstance();
                //把key和value存放容器中
                beans.put(key,value);
            }
            System.out.println("初始化容器结束-----");

        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据Bean的名字获取bean对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }
}
```
定义在bean.properties中的对象
```properties
accountService = com.zjw.service.impl.AccountServiceImpl
accountDao = com.zjw.dao.impl.AccountDaoImpl
```

## 测试类

```java
/**
 * 模拟一个表现层，用于调用业务层
 * 通过工厂类创建对象，需要创建的对象放在配置中间中
 * @author zjw
 */
public class Client {

    public static void main(String[] args) {

        for (int i=1 ;i<5;i++){
            IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
            System.out.println(accountService);
            accountService.saveAccount();
        }
    }
}
```
输出结果
```
初始化容器开始-----
初始化容器结束-----
com.zjw.service.impl.AccountServiceImpl@4554617c
AccountDaoImpl……保存成功
com.zjw.service.impl.AccountServiceImpl@4554617c
AccountDaoImpl……保存成功
com.zjw.service.impl.AccountServiceImpl@4554617c
AccountDaoImpl……保存成功
com.zjw.service.impl.AccountServiceImpl@4554617c
AccountDaoImpl……保存成功
```