package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.Setter;

import java.util.*;

/**
 * 账户的业务层实现类
 */
@Setter
public class AccountServiceImpl3 implements IAccountService {

    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;


    @Override
    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了");
        System.out.println(Arrays.toString(myStrs));
        System.out.println(myList);
        System.out.println(mySet);
        System.out.println(myMap);
        System.out.println(myProps);
    }

}
