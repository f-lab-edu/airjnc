package com.airjnc.room.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class SimpleRoom {

  private Long id;

  private String title;

  private Integer pricePerNight;

  private List<String> thumbnailList;
}
