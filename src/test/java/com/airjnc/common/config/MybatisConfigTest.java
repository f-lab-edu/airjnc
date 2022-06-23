package com.airjnc.common.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MybatisConfigTest {
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    public void checkMybatisConnection() throws SQLException {
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = dataSource.getConnection();
    }
    

}