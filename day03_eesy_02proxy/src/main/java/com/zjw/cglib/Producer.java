package com.zjw.cglib;

/**
 * 一个生产者
 * @author 朱俊伟
 */
public class Producer {

    /**
     * 销售
     * @param money 销售得到的金额
     */
    public void saleProduct(float money){
        System.out.println("销售产品，并拿到钱："+money);
    }

    /**
     * 售后
     * @param money 售后得到的金额
     */
    public void afterService(float money){
        System.out.println("提供售后服务，并拿到钱："+money);
    }
}
