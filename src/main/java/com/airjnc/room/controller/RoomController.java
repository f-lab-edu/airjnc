package com.airjnc.room.controller;

import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.SimpleRoom;
import com.airjnc.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public Page<SimpleRoom> getAll(@Validated @ModelAttribute RoomGetAllReq req, Pageable pageable) {
    return roomService.getAll(req, pageable);
  }
}
