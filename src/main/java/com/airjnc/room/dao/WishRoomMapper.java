package com.airjnc.room.dao;


import com.airjnc.room.dto.WishRoomDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WishRoomMapper {

  int create(@Param("userId") Long userId, @Param("roomId") Long roomId);

  int delete(Long id);

  boolean existsByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);

  Optional<WishRoomDto> findById(Long id);
}
