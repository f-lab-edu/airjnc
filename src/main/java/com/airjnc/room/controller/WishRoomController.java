package com.airjnc.room.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.room.service.WishRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishRoom")
@RequiredArgsConstructor
public class WishRoomController {

  private final WishRoomService wishRoomService;

  @PostMapping("/{roomId}")
  @CheckAuth
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@CurrentUserId Long userId, @PathVariable Long roomId) {
    wishRoomService.create(userId, roomId);
  }
}
