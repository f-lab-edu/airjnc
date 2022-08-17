CREATE TABLE IF NOT EXISTS `user`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `email`        VARCHAR(50)     NOT NULL,
    `password`     VARCHAR(100)    NOT NULL,
    `name`         VARCHAR(50)     NOT NULL,
    `gender`       VARCHAR(10),
    `phone_number` VARCHAR(20),
    `address`      VARCHAR(200),
    `birthdate`    TIMESTAMP,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`   TIMESTAMP,

    PRIMARY KEY (`id`),
    UNIQUE KEY `email-unique` (`email`),
    UNIQUE KEY `phone_number-unique` (`phone_number`)
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
    `id`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(20)     NOT NULL,
    `is_several_rooms` BOOLEAN         NOT NULL,

    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `room`
(
    `id`                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `title`                VARCHAR(50)     NOT NULL,
    `description`          TEXT            NOT NULL,
    `bed_count`            INT UNSIGNED    NOT NULL,           -- 침대 수
    `bedroom_count`        INT UNSIGNED    NOT NULL,           -- 침실 수
    `bathroom_count`       INT UNSIGNED    NOT NULL,           -- 욕실 수
    `limit_guests`         INT UNSIGNED    NOT NULL,           -- 최대 게스트 수
    `min_number_of_nights` INT UNSIGNED    NOT NULL,           -- 최소 숙박 일수
    `max_number_of_nights` INT UNSIGNED    NOT NULL,           -- 최대 숙박 일수
    `room_count`           INT UNSIGNED    NOT NULL DEFAULT 1, -- 방의 개수
    `check_in`             VARCHAR(2)      NOT NULL,           -- 체크인 시간
    `check_out`            VARCHAR(2)      NOT NULL,           -- 체크아웃 시간
    `address`              VARCHAR(200),
    -- content, description 종류 3가지
    -- IN_OPERATION : 영업 중, 게스트가 숙소를 검색하고 예약 요청을 보내거나 예약 가능 날짜를 예약할 수 있습니다.
    -- SHUT_DOWN : 운영 중지, 게스트가 숙소를 예약하거나 검색 결과에서 찾을 수 없습니다.
    -- DISABLED : 비활성화, 에어비앤비에서 영구적으로 숙소 비활성화
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
    `user_count`  INT UNSIGNED    NOT NULL, -- 예약 게스트 수
    `total_price` INT UNSIGNED    NOT NULL, -- 예약 가격
    `start_date`  TIMESTAMP       NOT NULL,
    `end_date`    TIMESTAMP       NOT NULL,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  TIMESTAMP,                -- Soft Delete

    `user_id`     BIGINT UNSIGNED NOT NULL,
    `room_id`     BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `user_id-idx` (`user_id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `wish_room`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP, -- Soft Delete

    `user_id`    BIGINT UNSIGNED NOT NULL,
    `room_id`    BIGINT UNSIGNED NOT NULL,

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

    `room_id`    BIGINT UNSIGNED NOT NULL,
    `user_id`    BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `room_id-idx` (`room_id`),
    KEY `user_id-idx` (`user_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `facility`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `category`    VARCHAR(20)     NOT NULL,
    `name`        VARCHAR(20)     NOT NULL,
    `description` VARCHAR(50) DEFAULT NULL,

    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- 다대다 테이블임을 명시적으로 보여주기 위해 "__"를 중간에 넣음
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

CREATE TABLE IF NOT EXISTS `room_price`
(
    `id`                    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `month`                 INT UNSIGNED    NOT NULL, -- 1,2,3,4,5,6,7,8,9,10,11,12 월
    `price`                 INT UNSIGNED    NOT NULL, -- 월별 기본 가격
    `weekly_discount_price` INT UNSIGNED    NOT NULL, -- 월별 주간 할인 가격
    `currency`              VARCHAR(20)     NOT NULL,

    `room_id`               BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (`id`),
    KEY `room_id-idx` (`room_id`)
) ENGINE = InnoDB;
