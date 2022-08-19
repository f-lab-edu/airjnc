package com.airjnc.room.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishRoom")
public class WishRoomController {

  @PostMapping("/{roomId}")
  @CheckAuth
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@CurrentUserId Long userId, @PathVariable Long roomId) {
  }
}
