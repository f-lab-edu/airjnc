package com.airjnc.room.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RoomSimpleResp {

  private Long id;

  private String title;

  private List<String> thumbnailList;

  private Integer price; // 1박당 가격
}
