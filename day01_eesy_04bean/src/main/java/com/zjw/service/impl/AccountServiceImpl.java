package com.zjw.service.impl;

import com.zjw.service.IAccountService;

/**
 * 账户的业务层实现类
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {


    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceImpl……saveAccount方法执行了");
    }

    public void init(){
        System.out.println("AccountServiceImpl……init方法执行了。。");
    }

    public void destroy(){
        System.out.println("AccountServiceImpl……destroy方法执行了。。");
    }
}
