package com.zjw.dao.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 * @author zjw
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    public QueryRunner runner;

    /**
     * 使用Lookup注解，告诉Spring这个方法需要使用查找方法注入
     * 这里直接使用@Lookup，则Spring将会依据方法返回值
     * 将它覆盖为一个在Spring容器中获取QueryRunner这个类型的bean的方法
     * 但是也可以指定需要获取的bean的名字，如：@Lookup("runner")
     * 此时，名字为runner的bean，类型必须与方法的返回值类型一致
     */
    @Lookup
    public QueryRunner getRunner(){
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
