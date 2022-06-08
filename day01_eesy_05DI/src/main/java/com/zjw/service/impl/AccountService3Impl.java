package com.zjw.service.impl;

import com.zjw.service.IAccountService;
import lombok.Setter;

import java.util.*;

/**
 * 账户的业务层实现类
 * @author 朱俊伟
 */
@Setter
public class AccountService3Impl implements IAccountService {

    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;


    @Override
    public void saveAccount() {
        System.out.println("accountService中的saveAccount方法执行了");
        System.out.println("数组myStrs: " + Arrays.toString(myStrs));
        System.out.println("List集合myList：" + myList);
        System.out.println("Set集合：" + mySet);
        System.out.println("Map集合：" + myMap);
        System.out.println("Properties：" + myProps);
    }

}
