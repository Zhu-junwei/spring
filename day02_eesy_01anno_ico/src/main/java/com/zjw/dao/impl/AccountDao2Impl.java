package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import org.springframework.stereotype.Repository;

/**
 * 账户的持久层实现类
 * @author zjw
 */
@Repository("accountDao")
public class AccountDao2Impl implements IAccountDao {

    @Override
    public void saveAccount() {
        System.out.println("AccountDao2Impl....保存成功");
    }
}
