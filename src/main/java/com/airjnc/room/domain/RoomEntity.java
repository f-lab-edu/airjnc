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

  private Integer pricePerNight;

  private Integer bedCount;

  private Integer bedroomCount;

  private Integer bathroomCount;

  private Integer roomCount;

  private Integer maxGuestCount;

  private Integer minNumberOfNights;

  private Integer maxNumberOfNights;

  private String checkIn;

  private String checkOut;

  private String address;

  private RoomStatus status;

  @Builder
  public RoomEntity(Long id, String title, String description, Integer pricePerNight, Integer bedCount,
      Integer bedroomCount, Integer bathroomCount, Integer roomCount, Integer maxGuestCount, Integer minNumberOfNights,
      Integer maxNumberOfNights, String checkIn, String checkOut, String address, RoomStatus status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.pricePerNight = pricePerNight;
    this.bedCount = bedCount;
    this.bedroomCount = bedroomCount;
    this.bathroomCount = bathroomCount;
    this.roomCount = roomCount;
    this.maxGuestCount = maxGuestCount;
    this.minNumberOfNights = minNumberOfNights;
    this.maxNumberOfNights = maxNumberOfNights;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.address = address;
    this.status = status;
  }
}
