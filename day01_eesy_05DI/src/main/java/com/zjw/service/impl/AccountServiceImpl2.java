package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 账户的业务层实现类
 */
@Setter
@ToString
public class AccountServiceImpl2 implements IAccountService {

    private String name;
    private Integer age;
    private Date birthday;

    @Override
    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了\n"+ this);
    }


}
