package com.testutil.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("unit")
@CommonTest
public @interface UnitTest {
}
