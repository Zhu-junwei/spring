package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;

    public QueryRunner getRunner() {
        return runner;
    }

    public List<Account> findAllAccount() {
        try {
            return getRunner().query("SELECT * FROM account",new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountById(Integer accountId) {
        try {
            return getRunner().query("SELECT * FROM account WHERE id=?",new BeanHandler<Account>(Account.class),accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAccount(Account account) {
        try {
            getRunner().update("INSERT INTO account(name,money) VALUES(?,?)",account.getName(),account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            getRunner().update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer accountId) {
        try {
            getRunner().update("DELETE FROM account WHERE id=?",accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
