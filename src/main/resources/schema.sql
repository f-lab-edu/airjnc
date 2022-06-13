CREATE TABLE IF NOT EXISTS `airbnb`.`address` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `detail` VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`user` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `gender` ENUM('MALE', 'FEMALE'),
    `birthdate` TIMESTAMP,
    `phone_number` VARCHAR(15),
    `is_active` BOOLEAN DEFAULT TRUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `auth` ENUM('USER', 'HOST', 'ADMIN') NOT NULL,
    
    `address_id` BIGINT UNSIGNED,
	
    PRIMARY KEY (`id`),
    FOREIGN KEY (`address_id`) REFERENCES `airbnb`.`address` (`id`)
) ENGINE = InnoDB;

-- 숙소 유형
CREATE TABLE IF NOT EXISTS `airbnb`.`room_category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  -- 숙소 제목
  `title` VARCHAR(50) NOT NULL,
  -- 숙소 설명
  `description` TEXT NOT NULL,
  -- 게스트 수
  `limit_guests` INT NOT NULL,
  -- 숙소 상태
  -- IN_OPERATION -> 게스트가 숙소를 검색하고 예약 요청을 보내거나 예약 가능 날짜를 예약할 수 있습니다.
  -- SHUT_DOWN -> 게스트가 숙소를 예약하거나, 검색 결과에서 찾을 수 없습니다.
  -- DISABLED -> 에어비앤비에서 영구적으로 숙소 비활성화
  `status` ENUM('IN_OPERATION', 'SHUT_DOWN', 'DISABLED') NOT NULL DEFAULT 'SHUT_DOWN',
  -- 숙소 종류
  -- 집 전체, 개인실, 다인실
  `type` ENUM('WHOLE', 'PRIVATE', 'SHARED') NOT NULL,
  -- 침대 수
  `beds` INT NOT NULL,
  -- 침실 수
  `bedrooms` INT NOT NULL,
  -- 욕실 수
  `baths` INT NOT NULL,
  -- 체크인 시간
  `check_in` TIMESTAMP NOT NULL,
  -- 체크아웃 시간
  `check_out` TIMESTAMP NOT NULL,
  -- 1박 가격
  `price_per_night` INT UNSIGNED NOT NULL,
  -- 최소 숙박 일수
  `min_number_of_nights` INT UNSIGNED NOT NULL,
  -- 최대 숙박 일수
  `max_number_of_nights` INT UNSIGNED NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  `user_id` BIGINT UNSIGNED NOT NULL,
  `category_id` BIGINT UNSIGNED NOT NULL,
  `address_id` BIGINT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `airbnb`.`room_category` (`id`),
  FOREIGN KEY (`address_id`) REFERENCES `airbnb`.`address` (`id`)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `airbnb`.`reservation` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` ENUM('PENDING', 'CONFIRMED', 'CANCELED') NOT NULL,
  `total_price` INT NOT NULL,
  `stay_start_date` TIMESTAMP NOT NULL,
  `stay_end_date` TIMESTAMP NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  `user_id` BIGINT UNSIGNED NOT NULL,
  `room_id` BIGINT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_facility_category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(10) NOT NULL,
  
  PRIMARY KEY (`id`)
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_facility` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  
  `category_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `airbnb`.`room_facility_category` (`id`)
)ENGINE = InnoDB;
  
CREATE TABLE IF NOT EXISTS `airbnb`.`room_room_facility` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_id` BIGINT UNSIGNED NOT NULL,
  `facility_id` BIGINT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_room_facility_unique` (`room_id`, `facility_id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`),
  FOREIGN KEY (`facility_id`) REFERENCES `airbnb`.`room_facility` (`id`)
  ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_photo` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `order` TINYINT UNSIGNED NULL DEFAULT '0',
  
  `room_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_review` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `price` TINYINT NOT NULL,
  `location` TINYINT NOT NULL,
  `cleanliness` TINYINT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  `room_id` BIGINT UNSIGNED NOT NULL,
  `user_id` BIGINT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE INDEX `room_review_unique` (`user_id`, `room_id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`)
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`wish_room` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `room_id` BIGINT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
)ENGINE = InnoDB;
