package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 账户的业务层实现类
 * @author 朱俊伟
 */
@Setter
@ToString
public class AccountService2Impl implements IAccountService {

    private String name;
    private Integer age;
    private Date birthday;

    @Override
    public void saveAccount() {
        System.out.println("AccountService2Impl……中的saveAccount方法执行了\n"+ this + "\n");
    }


}
