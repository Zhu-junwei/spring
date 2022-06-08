package com.zjw.service.impl;

import com.zjw.service.IAccountServiceThree;

/**
 * 账户的业务层实现类
 * 第三种方式：使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring）
 * @author 朱俊伟
 */
public class AccountServiceThreeImpl implements IAccountServiceThree {


    public AccountServiceThreeImpl() {
        System.out.println("AccountServiceThreeImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceThreeImpl中的saveAccount方法执行了");
    }

}
