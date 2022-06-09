CREATE TABLE IF NOT EXISTS `airbnb`.`address` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `detail` VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`user` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `gender` ENUM('MALE', 'FEMALE'),
    `birthdate` DATE,
    `phone_number` VARCHAR(15),
    `is_active` BOOLEAN DEFAULT TRUE,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    
    `address_id` INT UNSIGNED,
	
    PRIMARY KEY (`id`),
    FOREIGN KEY (`address_id`) REFERENCES `airbnb`.`address` (`id`)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_category` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  
  PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL,
  `type` ENUM('WHOLE', 'PRIVATE', 'SHARED') NOT NULL,
  `limit_guests` INT NOT NULL,
  `beds` INT NOT NULL,
  `bedrooms` INT NOT NULL,
  `baths` INT NOT NULL,
  `status` ENUM('IN_OPERATION', 'SHUT_DOWN', 'DISABLED') NOT NULL DEFAULT 'SHUT_DOWN',
  `check_in` DATETIME NOT NULL,
  `check_out` DATETIME NOT NULL,
  `smoking_allowed` TINYINT(1) NULL DEFAULT '0',
  `pets_allowed` TINYINT(1) NULL DEFAULT '0',
  `rule` TEXT NULL DEFAULT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  
  `user_id` INT UNSIGNED NOT NULL,
  `category_id` INT UNSIGNED NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `airbnb`.`room_category` (`id`),
  FOREIGN KEY (`address_id`) REFERENCES `airbnb`.`address` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`reservation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` ENUM('PENDING', 'CONFIRMED', 'CANCELED') NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  `stay_start_date` DATETIME NOT NULL,
  `stay_end_date` DATETIME NOT NULL,
  `total_price` INT NOT NULL,
  
  `user_id` INT UNSIGNED NOT NULL,
  `room_id` INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_facility_category` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(10) NOT NULL,
  
  PRIMARY KEY (`id`)
)ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `airbnb`.`room_facility` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  
  `category_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `airbnb`.`room_facility_category` (`id`)
  )ENGINE = InnoDB;
  
CREATE TABLE IF NOT EXISTS `airbnb`.`room_room_facility` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_id` INT UNSIGNED NOT NULL,
  `facility_id` INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_room_facility_unique` (`room_id`, `facility_id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`),
  FOREIGN KEY (`facility_id`) REFERENCES `airbnb`.`room_facility` (`id`)
  ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_photo` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `order` TINYINT UNSIGNED NULL DEFAULT '0',
  
  `room_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_price` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` INT UNSIGNED NOT NULL,
  
  `room_id` INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`room_review` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `price` TINYINT NOT NULL,
  `location` TINYINT NOT NULL,
  `cleanliness` TINYINT NOT NULL,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  
  `room_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE INDEX `room_review_unique` (`user_id`, `room_id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`)
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `airbnb`.`wish_room` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `room_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `airbnb`.`user` (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `airbnb`.`room` (`id`)
)ENGINE = InnoDB;
