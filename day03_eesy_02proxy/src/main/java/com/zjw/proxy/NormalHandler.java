package com.zjw.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 用于提供增强的代码
 * @author 朱俊伟
 */
public class NormalHandler implements InvocationHandler {

    /**
     * 要代理的对象
     */
    private final Object target;

    public NormalHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("NormalHandler...我对方法增强啦...");
        System.out.println("执行方法："+method.getName() + ",参数：" + Arrays.toString(args));
        //提供增强的代码
        Object returnValue = null ;
        //1、获取方法执行的参数
        Float money = (Float)args[0];
        //2、判断当前方法是不是销售
        if ("saleProduct".equals(method.getName())){
            returnValue =  method.invoke(target,money*0.8F);
        }else{
            //不需要代码增强
            returnValue =  method.invoke(target,args);
        }
        return returnValue;
    }
}
