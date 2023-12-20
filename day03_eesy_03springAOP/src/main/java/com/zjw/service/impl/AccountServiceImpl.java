package com.zjw.service.impl;

import com.zjw.service.IAccountService;

/**
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Override
    public void saveAccount() {
        System.out.println("saveAccount().....");
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    @Override
    public void deleteAccount() {
        System.out.println("deleteAccount()......");
    }
}
