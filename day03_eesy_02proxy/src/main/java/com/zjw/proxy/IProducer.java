package com.zjw.proxy;

/**
 * @author zjw
 */
public interface IProducer {

    /**
     * 销售
     * @param money 销售得到的金额
     */
    public void saleProduct(float money);

    /**
     * 售后
     * @param money 售后得到的金额
     */
    public void afterService(float money);
}
