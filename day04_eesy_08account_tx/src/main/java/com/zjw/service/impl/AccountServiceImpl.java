package com.zjw.service.impl;

import com.zjw.dao.IAccountDao;
import com.zjw.domain.Account;
import com.zjw.service.IAccountService;
import lombok.Setter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 账户的业务层实现类
 * <p>
 * 事务的控制应该都在业务层
 * @author zjw
 */
public class AccountServiceImpl implements IAccountService {

    @Setter
    private IAccountDao accountDao;

    @Setter
    private TransactionTemplate transactionTemplate ;

    @Override
    public Account findAccountById(Integer accountId) {
        return transactionTemplate.execute(new TransactionCallback<Account>() {
            @Override
            public Account doInTransaction(TransactionStatus status) {
                return accountDao.findAccountById(accountId);
            }
        });

    }

    /**
     * 声明式事务控制，代码太不优雅
     */
    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                //2、执行操作
                //2.1、根据名称查询转出账户
                Account source = accountDao.findAccountByName(sourceName);
                //2.2、根据名称查询转入账户
                Account target = accountDao.findAccountByName(targetName);
                //2.3、转出账户减钱
                source.setMoney(source.getMoney() - money);
                //2.4、转入账户加钱
                target.setMoney(target.getMoney() + money);
                //2.5、更新转出账户
                accountDao.updateAccount(source);

                int i = 1 / 0;

                //2.6、更新转入账户
                accountDao.updateAccount(target);
                return null;
            }
        });


    }
}
