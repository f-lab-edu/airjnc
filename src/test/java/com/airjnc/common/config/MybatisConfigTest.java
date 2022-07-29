package com.airjnc.common.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MybatisConfigTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void checkMybatisConnection() {
        SqlSession session = sqlSessionFactory.openSession();
    }


}