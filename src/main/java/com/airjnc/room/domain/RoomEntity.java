package com.airjnc.room.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomEntity {

  private Long id;

  private String title;

  private String description;

  private Integer bedCount;

  private Integer bedroomCount;

  private Integer bathroomCount;

  private Integer limitGuests;

  private Integer minNumberOfNights;

  private Integer maxNumberOfNights;

  private Integer roomCount;

  private String checkIn;

  private String checkOut;

  private String address;

  @Builder
  public RoomEntity(Long id, String title, String description, Integer bedCount, Integer bedroomCount,
      Integer bathroomCount, Integer limitGuests, Integer minNumberOfNights, Integer maxNumberOfNights,
      Integer roomCount, String checkIn, String checkOut, String address) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.bedCount = bedCount;
    this.bedroomCount = bedroomCount;
    this.bathroomCount = bathroomCount;
    this.limitGuests = limitGuests;
    this.minNumberOfNights = minNumberOfNights;
    this.maxNumberOfNights = maxNumberOfNights;
    this.roomCount = roomCount;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.address = address;
  }
}
