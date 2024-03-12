package com.zjw.construct.impl;

import com.zjw.construct.IAccountServiceA;
import com.zjw.construct.IAccountServiceB;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author 朱俊伟
 * @since 2024/03/12 10:34
 */
@Service("accountServiceA")
public class IAccountServiceAImpl implements IAccountServiceA {

    // 通过构造方法注入对象
    private final IAccountServiceB accountServiceB;

    public IAccountServiceAImpl(@Lazy IAccountServiceB accountServiceB) {
        this.accountServiceB = accountServiceB;
    }
}
