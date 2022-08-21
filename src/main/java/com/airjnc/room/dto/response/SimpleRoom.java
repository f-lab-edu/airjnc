package com.airjnc.room.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SimpleRoom {

  private Long id;

  private String title;

  private Integer price; // 1박당 가격

  private List<String> thumbnailList;
}
