package com.airjnc.room.dto.response;

import com.airjnc.room.domain.RoomStatus;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Room {

  private Long id;

  private String title;

  private String description;

  private Integer bedCount;

  private Integer bedroomCount;

  private Integer bathroomCount;

  private Integer maxGuestCount;

  private Integer roomCount;

  private Integer minNumberOfNights;

  private Integer maxNumberOfNights;

  private String checkIn;

  private String checkOut;

  private String address;

  private RoomStatus status;

  private List<String> photoList;

  @Builder
  public Room(Long id, String title, String description, Integer bedCount, Integer bedroomCount,
      Integer bathroomCount, Integer maxGuestCount, Integer roomCount, Integer minNumberOfNights,
      Integer maxNumberOfNights, String checkIn, String checkOut, String address, RoomStatus status,
      List<String> photoList) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.bedCount = bedCount;
    this.bedroomCount = bedroomCount;
    this.bathroomCount = bathroomCount;
    this.maxGuestCount = maxGuestCount;
    this.roomCount = roomCount;
    this.minNumberOfNights = minNumberOfNights;
    this.maxNumberOfNights = maxNumberOfNights;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.address = address;
    this.status = status;
    this.photoList = photoList;
  }
}
