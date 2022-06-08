package com.zjw.factory;

import com.zjw.service.IAccountServiceThree;
import com.zjw.service.impl.AccountServiceThreeImpl;

/**
 * @author 朱俊伟
 */
public class StaticFactory {

    public static IAccountServiceThree getAccountService(){
        return new AccountServiceThreeImpl();
    }
}
