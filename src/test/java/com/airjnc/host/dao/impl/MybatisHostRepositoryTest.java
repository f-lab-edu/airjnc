package com.airjnc.host.dao.impl;

import com.airjnc.host.dao.HostDAO;
import com.airjnc.host.dao.HostRepository;
import com.airjnc.host.dao.impl.MybatisHostRepository;
import com.airjnc.host.domain.HostEntity;
import com.testutil.annotation.MybatisTest;
import com.testutil.fixture.host.HostFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
class MybatisHostRepositoryTest {

    HostRepository hostRepository;

    @Autowired
    HostDAO hostDAO;

    HostEntity hostEntity;

    @BeforeEach
    void beforeEach() {
        hostEntity = HostFixture.getHostEntityBuilder().build();
        hostRepository = new MybatisHostRepository(hostDAO);
    }

    @Test
    @Transactional
    void createAndSelectHost() {
        //given
        //when
        Long hostId = hostRepository.insertHost(hostEntity);
        //then
        Assertions.assertThat(hostEntity.getId())
                .isEqualTo(hostRepository.selectHostByUserId(hostEntity.getUserId()).get()
                        .getId());
    }

    @Test
    @Transactional
    // 현재는 개발DB 데이터로 테스트진행 추후 roomCreate기능 구현 시 자체코드테스트 예정
    void findHostAndRoomByHostId() {
        //given
        Long hostId = 1L;
        //when
        HostEntity newHostEntity = hostRepository.selectHostAndRoomByHostId(hostId);
        //then
        Assertions.assertThat(newHostEntity.getUserId()).isEqualTo(1L);
        Assertions.assertThat(newHostEntity.getRoomList().size()).isEqualTo(2);
    }






}
