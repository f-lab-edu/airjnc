package com.testutil.annotation;

import com.testutil.config.TestDatabaseConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@IntegrationTest
@ExtendWith(SpringExtension.class) // Spring 관련 기능 확장
@ContextConfiguration(classes = TestDatabaseConfig.class) // 테스트용 컨텍스트 공유
@AutoConfigureMybatis // MyBatis slice 테스트 [ MyBatis 관련 빈들만 등록 ]
public @interface MybatisTest {

}
