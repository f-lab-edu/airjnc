package com.testutil.config;

import com.airjnc.common.config.MybatisConfig;
import com.airjnc.user.domain.UserEntity;
import com.testutil.testdata.TestUser;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
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

  private void clear(DriverManagerDataSource dataSource) {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.update("delete from `user` where `id` = 1");
  }

  @Bean
  DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
    clear(dataSource);
    initData(dataSource);
    return dataSource;
  }

  private void initData(DriverManagerDataSource dataSource) {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    String sql =
        "insert into `user` (id, email, password, name, gender, phone_number, address, is_active, birthdate, created_at, updated_at) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    UserEntity user = TestUser.getBuilder().build();
    template.update(
        sql,
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getName(),
        user.getGender().name(),
        user.getPhoneNumber(),
        user.getAddress(),
        user.getIsActive(),
        user.getBirthDate(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
