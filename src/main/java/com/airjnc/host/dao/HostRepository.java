package com.airjnc.host.dao;

import com.airjnc.host.domain.HostEntity;

public interface HostRepository {

    Long insertHost(HostEntity hostEntity);

    HostEntity selectHostByUserId(Long userId);
}
