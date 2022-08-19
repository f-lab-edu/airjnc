package com.testutil.config;

import com.airjnc.common.config.MybatisConfig;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration // 테스트용 설정 클래스
@Import(MybatisConfig.class) // MyBatis 설정 가져오기
public class TestDatabaseConfig {

  @Value("${spring.datasource.url}")
  String url;

  @Value("${spring.datasource.username}")
  String username;

  @Value("${spring.datasource.password}")
  String password;

  @Bean
  DataSource dataSource() {
    return new DriverManagerDataSource(url, username, password);
  }
}
