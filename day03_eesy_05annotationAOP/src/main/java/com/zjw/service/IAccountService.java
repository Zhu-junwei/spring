package com.zjw.service;

/**
 * 账户的业务层接口
 * @author 朱俊伟
 */
public interface IAccountService {

    /**
     * 保存账户
     */
    void saveAccount();

    /**
     *更新账户
     * @param i
     */
    void updateAccount(int i);

    /**
     * 删除账户
     * @return
     */
    int deleteAccount();
}
