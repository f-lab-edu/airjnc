package com.airjnc.admin.controller;

import com.airjnc.admin.dto.request.UpdateRoomReq;
import com.airjnc.admin.service.AdminService;
import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.room.dto.response.Room;
import com.airjnc.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PatchMapping("/rooms/{id}")
  @CheckAuth(role = UserRole.ADMIN)
  public Room updateRoom(@PathVariable Long id, @Validated @RequestBody UpdateRoomReq req) {
    return adminService.updateRoom(id, req);
  }
}
