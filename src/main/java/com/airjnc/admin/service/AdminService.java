package com.airjnc.admin.service;

import com.airjnc.admin.dao.AdminRepository;
import com.airjnc.admin.dto.request.UpdateRoomReq;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.response.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;

  private final RoomRepository roomRepository;

  public Room updateRoom(Long id, UpdateRoomReq req) {
    adminRepository.updateRoom(id, req.getStatus());
    return roomRepository.findById(id);
  }
}
