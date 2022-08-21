package com.airjnc.room.dao;

import com.airjnc.room.dto.response.Room;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomMapper {

  Optional<Room> findById(Long id);
}
