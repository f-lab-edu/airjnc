package com.airjnc.room.controller;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.SimpleRoom;
import com.airjnc.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public List<SimpleRoom> getAll(@Validated @RequestBody RoomGetAllReq req,
      @Validated @ModelAttribute Pageable pageable) {
    return roomService.getAll(req, pageable);
  }
}
