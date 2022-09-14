package com.airjnc.room.service;

import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public Page<SimpleRoom> getAll(RoomGetAllReq req, Pageable pageable) {
    FindAllByCategoryDto findAllByCategoryDto = FindAllByCategoryDto.builder()
        .categoryId(req.getCategoryId())
        .roomStatus(req.getStatus())
        .skip(pageable.getOffset() - pageable.getPageSize())
        .offset(pageable.getOffset())
        .build();
    List<SimpleRoom> list = roomRepository.findAllByCategory(findAllByCategoryDto);
    return new PageImpl<>(list, pageable, roomRepository.count(req.getCategoryId(), req.getStatus()));
  }
}
