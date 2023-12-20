package com.zjw.factory;

import com.zjw.service.IAccountServiceThree;
import com.zjw.service.impl.AccountServiceThreeImpl;

/**
 * @author zjw
 */
public class StaticFactory {

    public static IAccountServiceThree getAccountService(){
        return new AccountServiceThreeImpl();
    }
}
