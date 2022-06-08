package com.zjw.service.impl;

import com.zjw.service.IAccountServiceOne;

/**
 * 账户的业务层实现类
 * 对象创建的三种方式一：通过构造方法创建对象
 * @author 朱俊伟
 */
public class AccountServiceOneImpl implements IAccountServiceOne {


    public AccountServiceOneImpl() {
        System.out.println("AccountServiceOneImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceOneImpl中的saveAccount方法执行了");
    }

}
