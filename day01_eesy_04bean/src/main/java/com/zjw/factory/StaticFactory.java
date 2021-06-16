package com.zjw.factory;

import com.zjw.service.IAccountService;
import com.zjw.service.impl.AccountServiceImpl;

public class StaticFactory {

    public static IAccountService getAccountService(){
        return new AccountServiceImpl();
    }
}
