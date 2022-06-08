package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import org.springframework.stereotype.Repository;

/**
 * 账户的持久层实现类
 * @author 朱俊伟
 */
@Repository("accountDao1")
public class AccountDaoImpl implements IAccountDao {

    @Override
    public void saveAccount() {
        System.out.println("AccountDaoImpl....保存成功");
    }
}
