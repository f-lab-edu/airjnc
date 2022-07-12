package com.testutil.annotation;

import com.testutil.config.TestDatabaseConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CommonTest
@ContextConfiguration(classes = TestDatabaseConfig.class) // 테스트용 컨텍스트 공유
@ExtendWith(SpringExtension.class) // Spring 관련 기능 확장
public @interface MybatisTest {
}
