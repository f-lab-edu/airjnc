package com.testutil.annotation;

import com.testutil.config.TestDatabaseConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ContextConfiguration(classes = TestDatabaseConfig.class) // 테스트용 컨텍스트 공유
@ExtendWith(SpringExtension.class) // Spring 관련 기능 확장
@IntegrationTest
public @interface MybatisTest {

}
