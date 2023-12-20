package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.ToString;

import java.util.Date;

/**
 * 账户的业务层实现类
 * @author zjw
 */
@ToString
public class AccountServiceImpl implements IAccountService {

    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public AccountServiceImpl(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void saveAccount() {
        System.out.println("AccountServiceImpl……中的saveAccount方法执行了\n"+this.toString()+"\n");
    }

}
