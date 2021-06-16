package com.zjw.service.impl;

import com.zjw.service.IAccountService;

public class AccountServiceImpl implements IAccountService {

    public void saveAccount() {
        System.out.println("saveAccount().....");
    }

    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    public int deleteAccount() {
        System.out.println("deleteAccount()......");
        return 0;
    }
}
