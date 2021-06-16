package com.itheima.test;

import com.itheima.service.IAccountService;

public class MyThread extends Thread {

    private IAccountService as;

    public  MyThread(IAccountService as){
        this.as = as ;
    }

    /**
     * haha
     */
    @Override
    public synchronized void start() {
        System.out.println("123456");
        as.transfer("aaa","bbb",100f);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!!");
        String s = new String();

    }
}
