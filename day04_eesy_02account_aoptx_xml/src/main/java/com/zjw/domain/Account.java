package com.zjw.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 账户的实体类
 * @author 朱俊伟
 */
@Getter @Setter
@ToString
public class Account implements Serializable {

    /**
     * 账户id
     */
    private Integer id ;

    /**
     * 账户名称
     */
    private String name ;

    /**
     * 账户金额
     */
    private Float money ;
}