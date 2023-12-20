package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.dao.impl.AccountDaoImpl;
import com.zjw.service.IAccountService;

/**
 * 账户的业务层实现类
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = new AccountDaoImpl();

    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl……我创建了。。");
    }

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
