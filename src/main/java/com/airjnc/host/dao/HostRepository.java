package com.airjnc.host.dao;

import com.airjnc.host.domain.HostEntity;

import java.util.Optional;

public interface HostRepository {

    Long insertHost(HostEntity hostEntity);

    Optional<HostEntity> selectHostByUserId(Long userId);

    HostEntity selectHostAndRoomByHostId(Long hostId);

}
