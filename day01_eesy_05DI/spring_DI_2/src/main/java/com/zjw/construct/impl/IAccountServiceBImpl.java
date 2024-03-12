package com.zjw.construct.impl;

import com.zjw.construct.IAccountServiceA;
import com.zjw.construct.IAccountServiceB;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author 朱俊伟
 * @since 2024/03/12 10:34
 */
@Service("accountServiceB")
public class IAccountServiceBImpl implements IAccountServiceB {

    private IAccountServiceA accountServiceA;

    public IAccountServiceBImpl(@Lazy IAccountServiceA accountServiceA) {
        this.accountServiceA = accountServiceA;
    }
}
