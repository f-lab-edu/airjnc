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
        System.out.println(hostId);
        return hostId;
    }

    @Override
    public HostEntity selectHostByUserId(Long userId) {
        Optional<HostEntity> hostEntity = hostDAO.selectHostByUserId(userId);
        hostEntity.orElseThrow(NotFoundException::new);
        return hostEntity.get();
    }


}
