package com.zjw.service;

import com.zjw.domain.Account;

import java.util.List;

/**
 * 账户的业务层接口
 * @author zjw
 */
public interface IAccountService {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAllAccount();

    /**
     * 查询一个
     */
    Account findAccountById(Integer accountId);

    /**
     * 保存
     */
    void saveAccount(Account account);

    /**
     * 更新
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 删除
     * @param acccountId
     */
    void deleteAccount(Integer acccountId);

    /**
     *
     * 转账
     * @param sourceName    转出账户名称
     * @param targetName    出入账户名称
     * @param money         转账金额
     */
    void transfer(String sourceName,String targetName,Float money);


}




