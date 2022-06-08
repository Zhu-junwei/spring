package com.zjw.service.impl;

import com.zjw.service.IAccountService;

/**
 * @author 朱俊伟
 */
public class AccountServiceImpl implements IAccountService {

    @Override
    public void saveAccount() {
        System.out.println("saveAccount().....");
        int i = 1/0;
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    @Override
    public int deleteAccount() {
        System.out.println("deleteAccount()......");
        return 0;
    }
}
