package com.zjw.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 用于提供增强的代码
 */
public class NormalHandler implements InvocationHandler {

    //要代理的对象
    private Object target;

    //传入要代理的类
    public NormalHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("NormalHandler...我对方法增强啦...");
        return method.invoke(target,args);
    }
}
