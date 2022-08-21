package com.airjnc.room.service;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public List<SimpleRoom> getAll(RoomGetAllReq req, Pageable pageable) {
    return roomRepository.findAllByCategory(req.getCategoryId(), req.getStatus(), pageable);
  }
}
