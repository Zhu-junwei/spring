package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和事务管理相关的工具类，它包含了，开启事务，提交事务，回滚事务和释放连接
 */
@Component("txManager")
@Aspect
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt1(){}

    /**
     * 开启事务
     */
//    @Before("pt1()")
    public void beginTransaction(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 提交事务
     */
//    @AfterReturning("pt1()")
    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 回滚事务
     */
//    @AfterThrowing("pt1()")
    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 释放连接
     */
//    @After("pt1()")
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();
            connectionUtils.removeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Around("pt1()")
    public Object aroundAdvice(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            //1、获取参数
            Object[] args = pjp.getArgs();
            //开启事务
            this.beginTransaction();
            //执行方法
            rtValue = pjp.proceed(args);
            //提交事务
            this.commit();
            //返回结果
            return rtValue;
        }catch (Throwable t){
            this.rollback();
            throw new RuntimeException(t);
        }finally {
            //释放资源
            this.release();
        }
    }



}
