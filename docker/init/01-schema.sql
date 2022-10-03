CREATE TABLE IF NOT EXISTS `user`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(50)     NOT NULL,
    `password`   VARCHAR(100)    NOT NULL,
    `name`       VARCHAR(50)     NOT NULL,
    `gender`     VARCHAR(10),
    `address`    VARCHAR(200),
    `birthdate`  TIMESTAMP,
    -- DEFAULT -> 일반 유저
    -- HOST -> 호스팅하는 유저
    -- ADMIN -> 관리자
    `role`       VARCHAR(10)     NOT NULL DEFAULT 'DEFAULT',
    `created_at` TIMESTAMP                DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP,

    PRIMARY KEY (`id`),
    UNIQUE KEY `email-unique` (`email`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `host`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `photo_url`   VARCHAR(200),
    `description` VARCHAR(200),

    `user_id`     BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `user_id-idx` (`user_id`)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `room_category`
(
    `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20)     NOT NULL,

    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room`
(
    `id`                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `title`                VARCHAR(50)     NOT NULL,
    `description`          TEXT            NOT NULL,
    `pricePerNight`        INT UNSIGNED    NOT NULL,
    `bed_count`            INT UNSIGNED    NOT NULL,           -- 침대 수
    `bedroom_count`        INT UNSIGNED    NOT NULL,           -- 침실 수
    `bathroom_count`       INT UNSIGNED    NOT NULL,           -- 욕실 수
    `room_count`           INT UNSIGNED    NOT NULL DEFAULT 1, -- 예약할 수 있는 방의 개수 (1이면 한 날짜에 1방만 예약 가능, 2이면 한 날짜에 2방 예약 가능)
    `max_guest_count`      INT UNSIGNED    NOT NULL,           -- 최대 게스트 수
    `min_number_of_nights` INT UNSIGNED    NOT NULL,           -- 최소 숙박 일수
    `max_number_of_nights` INT UNSIGNED    NOT NULL,           -- 최대 숙박 일수
    `check_in`             VARCHAR(2)      NOT NULL,           -- 체크인 시간
    `check_out`            VARCHAR(2)      NOT NULL,           -- 체크아웃 시간
    `address`              VARCHAR(200),
    -- IN_OPERATION : 영업 중, 게스트가 숙소를 검색하고 예약 요청을 보내거나 예약 가능 날짜를 예약할 수 있습니다. [영업 중]
    -- SHUT_DOWN : 운영 중지, 게스트가 숙소를 예약하거나 검색 결과에서 찾을 수 없습니다. [(A)호스트가 자발적으로 했다거나, (B)호스트 처음 신청 후 어드민이 아직 수락을 안한 상태]
    -- DISABLED : 비활성화, 에어비앤비에서 영구적으로 숙소 비활성화 [호스트가 해당 방을 제거함 -> deleted_at에 해당 날짜가 입력됨]
    `status`               VARCHAR(20)     NOT NULL,
    `created_at`           TIMESTAMP                DEFAULT CURRENT_TIMESTAMP,
    `updated_at`           TIMESTAMP                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`           TIMESTAMP,                          -- Soft Delete

    `host_id`              BIGINT UNSIGNED NOT NULL,
    `room_category_id`     BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `host_id-idx` (`host_id`),
    KEY `room_category_id-idx` (`room_category_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room_reservation`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_count`  INT             NOT NULL, -- 예약한 명 수
    --
    `start_date`  TIMESTAMP       NOT NULL,
    `end_date`    TIMESTAMP       NOT NULL,
    `total_price` INT UNSIGNED    NOT NULL,
    --
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  TIMESTAMP,                -- Soft Delete

    `user_id`     BIGINT UNSIGNED NOT NULL,
    `room_id`     BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `user_id-idx` (`user_id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room_reservation_date`
(
    `id`                  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `date`                TIMESTAMP       NOT NULL,
    `is_canceled`         BOOLEAN         NOT NULL DEFAULT FALSE, -- 환불 여부: TRUE->환불, FALSE->예약완료

    `room_id`             BIGINT UNSIGNED NOT NULL,
    `room_reservation_id` BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `room_id-idx` (`room_id`),
    KEY `room_reservation_id-idx` (`room_reservation_id`)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `wish_room`
(
    `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,

    `user_id` BIGINT UNSIGNED NOT NULL,
    `room_id` BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE KEY `wish_room-unique` (`user_id`, `room_id`),
    KEY `user_id-idx` (`user_id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room_review`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `content`    TEXT            NOT NULL,
    `like`       INT UNSIGNED    NOT NULL, -- 좋아요 1~5점 [에어비앤비는 좋아요가 아니고 5개정도? 있지만 현 프로젝트는 "좋아요" 하나로만]
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP,                -- Soft Delete

    `user_id`    BIGINT UNSIGNED NOT NULL,
    `room_id`    BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `user_id-idx` (`user_id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `facility`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `category`    VARCHAR(20)     NOT NULL,
    `name`        VARCHAR(20)     NOT NULL,
    `description` VARCHAR(50) DEFAULT NULL,

    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room__facility`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,

    `room_id`     BIGINT UNSIGNED NOT NULL,
    `facility_id` BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE KEY `room__facility-unique` (`room_id`, `facility_id`), -- 특정 방에 대해서 편의시설 중복 방지
    KEY `room_id-idx` (`room_id`),
    KEY `facility_id-idx` (`facility_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room_photo`
(
    `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `url`     VARCHAR(255)    NOT NULL,
    `order`   INT UNSIGNED, -- 방에서 보여줄 사진의 우선순위 설정

    `room_id` BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;
