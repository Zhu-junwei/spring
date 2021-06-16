package com.zjw.test;

import com.zjw.proxy.IProducer;
import com.zjw.proxy.NormalHandler;
import com.zjw.proxy.Producer;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class TestClass {

    @Test
    public void testProducer(){
        ClassLoader classLoader = Producer.class.getClassLoader();
        System.out.println(classLoader);
    }

    @Test
    public void testProxyProducer(){
        IProducer producer = (IProducer) Proxy.newProxyInstance(Producer.class.getClassLoader(), new Class[]{IProducer.class}, new NormalHandler(new Producer()));
        producer.afterService(100F);
    }
}
