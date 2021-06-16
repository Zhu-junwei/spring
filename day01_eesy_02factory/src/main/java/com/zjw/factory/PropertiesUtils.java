package com.zjw.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * @author zjw
 */
public class PropertiesUtils {

    //定义一个Properties对象
    private static Properties properties;

    //定义一个Map，用于存放我们的文件的key、value
    private static Map<String,String> proMap = new HashMap<>();

    //使用静态代码块为Properties对象赋值
    static {
        try {
            //实例化对象
            properties = new Properties();
            //获取properties文件的流对象
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("bean.properties");
            properties.load(in);
            //取出配置文件中所有的key
            String name = properties.getProperty("name");
            System.out.println(name);
            Enumeration keys = properties.keys();
            //遍历枚举
            while (keys.hasMoreElements()){
                String key = keys.nextElement().toString();
                //根据key获取value
                String value = properties.getProperty(key);
                proMap.put(key,value);
            }

        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据Bean的名字获取bean对象
     * @param key
     * @return
     */
    public static String getValue(String key){
        return proMap.get(key);
    }

}
