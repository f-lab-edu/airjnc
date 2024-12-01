package com.airjnc.host.service;

import com.airjnc.host.dao.HostRepository;
import com.airjnc.host.domain.HostEntity;
import com.airjnc.host.dto.response.HostResp;
import com.airjnc.host.etc.HostMapper;
import com.airjnc.host.etc.HostMapperImpl;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.host.HostFixture;
import io.netty.resolver.HostsFileEntries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@UnitTest
class HostServiceTest {

    @Mock
    HostRepository hostRepository;

    @Mock
    HostMapper hostMapper;

    @InjectMocks
    HostService hostService;

    HostEntity hostEntity;

    @BeforeEach
    void beforeEach() {
        hostEntity = HostFixture.getHostEntityBuilder().build();
    }

    @Test()
    void getHostResp() {
        //given
        given(hostRepository.selectHostByUserId(any())).willReturn(Optional.of(hostEntity));
        given(hostRepository.selectHostAndRoomByHostId(any())).willReturn(hostEntity);
        given(hostMapper.hostToHostResp(any())).willReturn(HostResp.builder().id(hostEntity.getId()).userId(hostEntity.getUserId()).build());

        //when
        HostResp hostResp = hostService.changeUserToHostMode(hostEntity.getUserId());

        //then
        Assertions.assertThat(hostResp.getId()).isEqualTo(hostEntity.getId());
        Assertions.assertThat(hostResp.getUserId()).isEqualTo(hostEntity.getUserId());

    }


}
