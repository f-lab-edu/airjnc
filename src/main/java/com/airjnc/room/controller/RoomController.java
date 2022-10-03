package com.airjnc.room.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.reservation.service.ReservationService;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.dto.response.SimpleRoom;
import com.airjnc.room.service.RoomService;
import com.airjnc.room.service.WishRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;
  private final WishRoomService wishRoomService;

  private final RoomRepository roomRepository;
  private final ReservationService reservationService;

  @GetMapping
  public Page<SimpleRoom> getAll(@Validated @ModelAttribute RoomGetAllReq req, Pageable pageable) {
    return roomService.getAll(req, pageable);
  }

  @GetMapping("/{id}")
  public Room getOne(@PathVariable Long id) {
    return roomRepository.findById(id);
  }

  @PostMapping("/{id}/wish")
  @CheckAuth
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@CurrentUserId Long userId, @PathVariable Long id) {
    wishRoomService.create(userId, id);
  }

  @PostMapping("/{id}/reservation")
  @CheckAuth
  @ResponseStatus(HttpStatus.CREATED)
  public void reservation(@CurrentUserId Long userId, @PathVariable Long roomId,
                          @Validated @RequestBody ReservationReq req) {
    reservationService.reservation(userId, roomId, req);
  }
}
