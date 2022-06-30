package com.airjnc.util.annotation;

import com.airjnc.util.config.DatabaseConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@ExtendWith(SpringExtension.class) // Spring 관련 기능 확장
@AutoConfigureMybatis // MyBatis slice 테스트 [ MyBatis 관련 빈들만 등록 ]
@ContextConfiguration(classes = DatabaseConfig.class) // 테스트용 컨텍스트 공유
@TestPropertySource(locations = "classpath:/application-test.properties") // 테스트용 properties 설정‘
public @interface MybatisTest {
}
