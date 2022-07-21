package com.airjnc.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCut {

  @Pointcut("@annotation(com.airjnc.common.annotation.CheckAuth)")
  public void checkAuth() {
  }
}
