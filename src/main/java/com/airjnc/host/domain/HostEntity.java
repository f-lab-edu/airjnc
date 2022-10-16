package com.airjnc.host.domain;

import com.airjnc.room.domain.RoomEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class HostEntity {

    private Long id;

    private String photoURL;

    private String description;

    private Long userId;

    private List<RoomEntity> roomList;

    @Builder
    public HostEntity(Long id, String photoURL, String description, Long userId, List<RoomEntity> roomList) {
        this.id = id;
        this.photoURL = photoURL;
        this.description = description;
        this.userId = userId;
        this.roomList = roomList;
    }

    public String getDefaultDescription(String name) {
        return String.format("안녕하세요. 저는 %s 입니다.", name);
    }
}


