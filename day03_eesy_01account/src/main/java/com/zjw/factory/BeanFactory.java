package com.zjw.factory;

import com.zjw.service.IAccountService;
import com.zjw.utils.TransactionManager;
import lombok.Setter;

import java.lang.reflect.Proxy;

/**
 * 用于创建service的代理对象的工厂
 * @author zjw
 */
public class BeanFactory {

    @Setter
    private IAccountService accountService ;

    @Setter
    private TransactionManager txManager;

    public IAccountService getAccountService(){
        return (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object rtValue ;
                    try {
                        //1、开启事务
                        txManager.beginTransaction();
                        //2、执行操作
                        rtValue = method.invoke(accountService,args);
                        //3、提交事务
                        txManager.commit();
                        //4、返回结果
                        return rtValue;
                    }catch (Exception e){
                        //5、回滚操作
                        txManager.rollback();
                        throw new RuntimeException(e);
                    }finally {
                        //6、释放连接
                        txManager.release();
                    }
                });

    }
}
