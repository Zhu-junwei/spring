package com.zjw.proxy;

import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 * @author 朱俊伟
 */
public class Client2 {
    public static void main(String[] args) {
        Producer producer = new Producer();

        //创建代理对象
        IProducer proxyProducer =  (IProducer)Proxy.newProxyInstance(
                producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new NormalHandler(producer));

        //通过代理对象执行方法
        proxyProducer.saleProduct(10000f);
        proxyProducer.afterService(10000f);
    }
}
