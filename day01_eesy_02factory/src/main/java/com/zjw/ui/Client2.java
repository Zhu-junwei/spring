package com.zjw.ui;

import com.zjw.factory.PropertiesUtils;

/**
 * @author 朱俊伟
 * @date 2021/06/16
 */
public class Client2 {
    public static void main(String[] args) {
        String value = PropertiesUtils.getValue("name");
        System.out.println(value);
    }
}
