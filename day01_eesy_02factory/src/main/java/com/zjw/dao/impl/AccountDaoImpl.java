package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;

/**
 * 账户的持久层实现类
 * @author 朱俊伟
 */
public class AccountDaoImpl implements IAccountDao {

    @Override
    public void saveAccount() {
        System.out.println("AccountDaoImpl……保存成功");
    }
}
