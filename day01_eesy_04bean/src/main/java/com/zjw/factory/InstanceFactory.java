package com.zjw.factory;

import com.zjw.service.IAccountService;
import com.zjw.service.impl.AccountServiceImpl;

public class InstanceFactory {

    public IAccountService getAccountService(){
        return new AccountServiceImpl();
    }
}
