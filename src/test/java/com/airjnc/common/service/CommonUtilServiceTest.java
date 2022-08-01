package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.exception.NotFoundException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class CommonUtilServiceTest {

  @Mock
  RedisDao redisDao;

  @InjectMocks
  CommonUtilService commonUtilService;

  @Test
  void generateCodeOnce() {
    //given
    given(redisDao.get(anyString())).willThrow(NotFoundException.class);
    //when
    String code = commonUtilService.generateCode();
    //then
    assertThat(code.length()).isEqualTo(6);
    then(redisDao).should(times(1)).get(code);
  }

  @Test
  void generateCodeTwice() {
    //given
    given(redisDao.get(anyString())).willReturn(anyString()).willThrow(NotFoundException.class);
    //when
    String code = commonUtilService.generateCode();
    //then
    assertThat(code.length()).isEqualTo(6);
    then(redisDao).should(times(2)).get(anyString());
  }
}