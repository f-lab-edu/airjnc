package com.airjnc.host.dao.impl;


import com.airjnc.common.exception.NotFoundException;
import com.airjnc.host.dao.HostDAO;
import com.airjnc.host.dao.HostRepository;
import com.airjnc.host.domain.HostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisHostRepository implements HostRepository {

    private final HostDAO hostDAO;

    @Override
    public Long insertHost(HostEntity hostEntity) {
        System.out.println(hostEntity.toString());
        Long hostId = hostDAO.insertHost(hostEntity);
        return hostId;
    }

    @Override
    public Optional<HostEntity> selectHostByUserId(Long userId) {
        Optional<HostEntity> hostEntity = hostDAO.selectHostByUserId(userId);
        return hostEntity;
    }

    @Override
    public HostEntity selectHostAndRoomByHostId(Long hostId) {
        HostEntity hostEntity = hostDAO.selectHostAndRoomByHostId(hostId);
        return hostEntity;
    }
}
