package com.zjw.service.impl;

import com.zjw.service.IAccountServiceTwo;

/**
 * 账户的业务层实现类
 * 第二种方式：使用普通工厂中的方法创建对象（使用某个类中的方法创建对象，并存入spring容器）
 * @author 朱俊伟
 */
public class AccountServiceTwoImpl implements IAccountServiceTwo {


    public AccountServiceTwoImpl() {
        System.out.println("AccountServiceTwoImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceTwoImpl中的saveAccount方法执行了");
    }

}
