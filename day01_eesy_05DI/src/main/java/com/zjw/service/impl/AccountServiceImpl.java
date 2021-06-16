package com.zjw.service.impl;

import com.zjw.service.IAccountService;

import java.util.Date;
import java.util.UUID;

/**
 * 账户的业务层实现类
 */
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

    public AccountServiceImpl() {}

    @Override
    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了\n"+this.toString());
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "AccountServiceImpl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
