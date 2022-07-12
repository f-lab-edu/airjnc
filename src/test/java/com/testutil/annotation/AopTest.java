package com.testutil.annotation;

import com.testutil.config.TestAopConfig;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ContextConfiguration(classes = TestAopConfig.class)
@IntegrationTest
public @interface AopTest {
}
