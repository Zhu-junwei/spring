package com.zjw.test;

import com.zjw.config.SpringConfiguration;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QueryRunnerTest {

    @Test
    public void testQueryRunnerTest(){
        // 验证多例
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        QueryRunner runner1 = (QueryRunner) ac.getBean("runner");
        QueryRunner runner2 = (QueryRunner) ac.getBean("runner");
        System.out.println(runner1);
        System.out.println(runner2);
        System.out.println(runner1==runner2);

    }
}
