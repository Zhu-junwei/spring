package com.zjw.utils;

/**
 * 作为切面
 * 用于记录日志的工具类，它里面提供了公共的代码
 * @author zjw
 */
public class Logger {

    /**
     * 用于打印日志：计划让其再切入点方法执行之前执行（切入点方法就是业务层方法）
     */
    public void printLog(){
        System.out.println("...........printLog().........");
    }
}
