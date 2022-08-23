package com.airjnc.host.dao.impl;

import com.airjnc.host.dao.impl.MybatisHostRepository;
import com.airjnc.host.domain.HostEntity;
import com.testutil.fixture.host.HostFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MybatisHostRepositoryTest {

//    @Spy
//    HostDAO hostDAO;

    @Autowired
    MybatisHostRepository hostRepository;


    HostEntity hostEntity;

    @BeforeEach
    void beforeEach() {
        hostEntity = HostFixture.getHostEntityBuilder().build();
    }

    @Test
    @Transactional
    void create() {
        //given
        //when
        Long hostId = hostRepository.insertHost(hostEntity);
        //then
        Assertions.assertThat(hostId)
                .isEqualTo(hostRepository.selectHostByUserId(hostEntity.getUserId())
                        .getId());
    }






}
