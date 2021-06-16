package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    public void saveAccount() {
        System.out.println("saveAccount().....");
        int i = 1/0;
    }

    public void updateAccount(int i) {
        System.out.println("updateAccount(int i)...."+i);
    }

    public int deleteAccount() {
        System.out.println("deleteAccount()......");
        return 0;
    }
}
