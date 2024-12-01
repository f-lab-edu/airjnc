package com.airjnc.host.etc;

import com.airjnc.host.domain.HostEntity;
import com.airjnc.host.dto.response.HostResp;
import com.airjnc.host.dto.response.RoomResp;
import com.airjnc.room.domain.RoomEntity;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.WARN, unmappedSourcePolicy = ReportingPolicy.WARN,
        componentModel = MappingConstants.ComponentModel.SPRING, uses = HostMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HostMapper {

    @Mapping(target = "roomRespList", ignore = true)
    HostResp hostToHostResp(HostEntity hostEntity);
}
