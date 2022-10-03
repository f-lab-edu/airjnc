package com.airjnc.admin.dao.impl;

import com.airjnc.admin.dao.AdminMapper;
import com.airjnc.admin.dao.AdminRepository;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.room.domain.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisAdminRepository implements AdminRepository {

  private final AdminMapper adminMapper;

  private final CommonValidateService commonValidateService;

  @Override
  public void updateRoom(Long id, RoomStatus status) {
    int affect = adminMapper.updateRoom(id, status);
    commonValidateService.shouldBeMatch(affect, 1);
  }
}
