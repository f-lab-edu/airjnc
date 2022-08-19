package com.airjnc.room.dao;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.RoomSimpleResp;
import java.util.List;

public interface RoomRepository {

  List<RoomSimpleResp> findAllByCategory(Long categoryId, RoomStatus roomStatus, Pageable pageable);
}
