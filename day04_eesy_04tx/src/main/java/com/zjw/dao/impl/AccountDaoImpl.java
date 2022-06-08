package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author 朱俊伟
 */
public class AccountDaoImpl extends JdbcDaoSupport implements IAccountDao {

    @Override
    public Account findAccountById(Integer accountId) {
        List<Account> accounts = getJdbcTemplate().query("SELECT * FROM account WHERE id=?", new BeanPropertyRowMapper<>(Account.class),accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = getJdbcTemplate().query("SELECT * FROM account WHERE name=?", new BeanPropertyRowMapper<>(Account.class),accountName);
        if (accounts.isEmpty()){
            return null;
        }
        if (accounts.size()>1){
            throw new RuntimeException("结果集不唯一");
        }
        return accounts.get(0);
    }

    @Override
    public void updateAccount(Account account) {
        getJdbcTemplate().update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
    }
}
