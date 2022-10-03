package com.airjnc.room.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.room.service.WishRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishRooms")
@RequiredArgsConstructor
public class WishRoomController {

  private final WishRoomService wishRoomService;

  @DeleteMapping("/{id}")
  @CheckAuth
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@CurrentUserId Long userId, @PathVariable Long id) {
    wishRoomService.delete(userId, id);
  }
}
