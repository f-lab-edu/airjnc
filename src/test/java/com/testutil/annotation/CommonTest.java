package com.testutil.annotation;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:/application-test.properties") // 테스트용 properties 설정
public @interface CommonTest {
}
