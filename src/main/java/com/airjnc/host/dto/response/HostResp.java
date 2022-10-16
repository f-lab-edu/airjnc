package com.airjnc.host.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HostResp {

    private Long id;

    private Long userId;

    private List<RoomResp> roomRespList;

    @Builder
    public HostResp(Long id, Long userId, List<RoomResp> roomRespList) {
        this.id = id;
        this.userId = userId;
        this.roomRespList = roomRespList;
    }
}
