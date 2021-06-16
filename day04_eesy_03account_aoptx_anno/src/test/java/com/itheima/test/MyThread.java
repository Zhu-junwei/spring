package com.itheima.test;

import com.itheima.service.IAccountService;

public class MyThread extends Thread {
    private IAccountService as;
    public  MyThread(IAccountService as){
        this.as = as ;
    }
    @Override
    public synchronized void start() {
        System.out.println("123456");
        as.transfer("aaa","bbb",100f);
    }
}
