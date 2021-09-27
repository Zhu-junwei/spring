package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * 账户的业务层实现类
 */
@Data
@NoArgsConstructor
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
        System.out.println("accountService中的saveAccount方法执行了\n"+this.toString());
    }

}
