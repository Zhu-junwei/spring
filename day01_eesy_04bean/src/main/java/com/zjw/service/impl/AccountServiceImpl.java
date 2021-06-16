package com.zjw.service.impl;

import com.zjw.service.IAccountService;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {


    public AccountServiceImpl() {
        System.out.println("我创建了。。");
    }

    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了");
    }

    public void init(){
        System.out.println("init方法执行了。。");
    }

    public void destroy(){
        System.out.println("destroy方法执行了。。");
    }
}
