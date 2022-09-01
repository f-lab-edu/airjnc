package com.airjnc.host.service;

import com.airjnc.host.dao.HostRepository;
import com.airjnc.host.domain.HostEntity;
import com.airjnc.host.dto.response.HostResp;
import com.airjnc.host.etc.HostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final HostMapper hostMapper;

    public HostResp changeUserToHostMode(Long userId) {
        Long hostId = checkExistsHostAndCreateOrSelectHostId(userId);
        HostEntity host = hostRepository.selectHostAndRoomByHostId(hostId);
        HostResp hostResp = hostMapper.hostToHostResp(host);
        return hostResp;

    }

    private Long checkExistsHostAndCreateOrSelectHostId(Long userId) {
        Optional<HostEntity> hostEntity = hostRepository.selectHostByUserId(userId);
        if (hostEntity.isEmpty()) {
            Long hostId = hostRepository.insertHost(HostEntity.builder().userId(userId).build());
            return hostId;
        } else {
            Long hostId = hostEntity.get().getId();
            return hostId;
        }

    }

}
