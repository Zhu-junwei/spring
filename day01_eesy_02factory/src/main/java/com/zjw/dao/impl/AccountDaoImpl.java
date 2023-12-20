package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;

/**
 * 账户的持久层实现类
 * @author zjw
 */
public class AccountDaoImpl implements IAccountDao {

    @Override
    public void saveAccount() {
        System.out.println("AccountDaoImpl……保存成功");
    }
}
