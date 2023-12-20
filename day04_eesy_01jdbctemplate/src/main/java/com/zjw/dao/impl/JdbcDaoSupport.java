package com.zjw.dao.impl;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zjw
 */
public class JdbcDaoSupport {

    @Getter
    private JdbcTemplate jdbcTemplate ;

    public void setDataSource(DataSource dataSource){
        if (jdbcTemplate == null){
            jdbcTemplate = createJdbcTemplate(dataSource);
        }
    }

    private JdbcTemplate createJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
