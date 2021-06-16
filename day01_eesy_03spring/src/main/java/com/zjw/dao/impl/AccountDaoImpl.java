package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;

/**
 * 账户的持久层实现类
 * @author zjw
 */
public class AccountDaoImpl implements IAccountDao {

    public AccountDaoImpl() {
        System.out.println("AccountDaoImpl创建了。。。");
    }

    @Override
    public void saveAccount() {
        System.out.println("保存成功");
    }
}
