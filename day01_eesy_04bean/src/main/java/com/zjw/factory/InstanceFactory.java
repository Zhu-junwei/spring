package com.zjw.factory;

import com.zjw.service.IAccountServiceTwo;
import com.zjw.service.impl.AccountServiceTwoImpl;

/**
 * @author zjw
 */
public class InstanceFactory {

    public IAccountServiceTwo getAccountService(){
        return new AccountServiceTwoImpl();
    }
}
