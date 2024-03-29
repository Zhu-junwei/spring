package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 * @author zjw
 */
//@Getter
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;

    // 虽然有get方法，但是并不会执行，而是由容器提供一个动态代理的实现
    public QueryRunner getRunner() {
        System.out.println("getRunner方法执行....");
        return runner;
    }

    @Override
    public List<Account> findAllAccount() {
        try {
            return getRunner().query("SELECT * FROM account",new BeanListHandler<>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {
        try {
            return getRunner().query("SELECT * FROM account WHERE id=?",new BeanHandler<>(Account.class),accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            getRunner().update("INSERT INTO account(name,money) VALUES(?,?)",account.getName(),account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            getRunner().update("UPDATE account SET name=?,money=? WHERE id=?",account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {
        try {
            getRunner().update("DELETE FROM account WHERE id=?",accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
