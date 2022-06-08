package com.zjw.factory;

import com.zjw.service.IAccountServiceTwo;
import com.zjw.service.impl.AccountServiceTwoImpl;

/**
 * @author 朱俊伟
 */
public class InstanceFactory {

    public IAccountServiceTwo getAccountService(){
        return new AccountServiceTwoImpl();
    }
}
