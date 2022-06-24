package com.airjnc.util.config;

import com.airjnc.common.config.MybatisConfig;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.util.fixture.UserEntityFixture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration // 테스트용 설정 클래스
@Import(MybatisConfig.class) // MyBatis 설정 가져오기
public class DatabaseConfig {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(this.url, this.username, this.password);
        clear(dataSource);
        initData(dataSource);
        return dataSource;
    }

    private void clear(DriverManagerDataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("delete from `user` where `id` = 1");
    }

    private void initData(DriverManagerDataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        UserEntity user = UserEntityFixture.getBuilder().build();
        template.update("insert into `user` (id, email, password, name, gender, phone_number, address, is_active," +
                " " +
                "birthdate, created_at, updated_at, deleted_at)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getGender().name(),
            user.getPhoneNumber(),
            user.getAddress(), user.isActive(), user.getBirthDate(), user.getCreatedAt(), user.getUpdatedAt(),
            user.getDeletedAt()
        );
    }

}
