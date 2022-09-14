package com.airjnc.reservation.dto.request;


import com.airjnc.common.annotation.TwoFieldDate;
import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TwoFieldDate(first = "startDate", second = "endDate")
public class ReservationReq {

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate startDate;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate endDate;

  @NotNull
  Integer guestCount;

  @Builder
  public ReservationReq(LocalDate startDate, LocalDate endDate, Integer guestCount) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.guestCount = guestCount;
  }

  public List<ReservationDateEntity> toReservationEntityList(Long roomId, Long roomReservationId) {
    return Stream.of(getStartDate(), getEndDate())
        .map(
            date -> ReservationDateEntity.builder()
                .date(date)
                .isCanceled(false)
                .roomId(roomId)
                .roomReservationId(roomReservationId)
                .build()
        ).collect(Collectors.toList());
  }

  public ReservationEntity toReservationEntity(Long userId, Long roomId, Integer pricePerNight) {
    return ReservationEntity.builder()
        .userCount(getGuestCount())
        .startDate(getStartDate())
        .endDate(getEndDate())
        .pricePerNight(pricePerNight)
        .userId(userId)
        .roomId(roomId)
        .build();
  }
}
