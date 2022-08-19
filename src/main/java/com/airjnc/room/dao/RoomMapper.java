package com.airjnc.room.dao;

import com.airjnc.room.dto.response.RoomDetailResp;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomMapper {

  Optional<RoomDetailResp> findById(Long id);
}
