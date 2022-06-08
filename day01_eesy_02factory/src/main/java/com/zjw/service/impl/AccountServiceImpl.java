package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.factory.BeanFactory;
import com.zjw.service.IAccountService;

/**
 * 账户的业务层实现类
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {


    private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
