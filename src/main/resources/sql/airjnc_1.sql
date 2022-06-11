CREATE TABLE `room` (
                        `id` bigint auto_increment primary key,
                        `title` varchar(50),
                        `description` varchar(1000),
                        `bedroom_count` int,
                        `bathroom_count` int,
                        `bed_count` int,
                        `max_people` int,
                        `host_question` varchar(1000),
                        `room_checkin_time` varchar(20),
                        `room_checkout_time` varchar(20),
                        `host_room_address_id` bigint,
                        `host_id` varchar(200)
);

CREATE TABLE `room_price` (
                              `id` bigint auto_increment primary key,
                              `start_date` datetime,
                              `end_date` datetime,
                              `price` bigint,
                              `currency` varchar(20),
                              `room_id` bigint);

CREATE TABLE `room_facility_code` (
                                      `id` bigint auto_increment primary key,
                                      `upr_id` bigint,
                                      `name` varchar(50),
                                      `description` varchar(200)
);

CREATE TABLE `room_facility` (
                                 `id` bigint auto_increment primary key,
                                 `room_id` bigint,
                                 `facility_id` varchar(50)
);

CREATE TABLE `user` (
                        `id` bigint auto_increment primary key,
                        `email` varchar(50),
                        `password` varchar(100),
                        `name` varchar(100),
                        `nickname` varchar(100),
                        `gender` varchar(20),
                        `birthdate` datetime,
                        `address` varchar(200),
                        `phone_number` varchar(20),
                        `active_yn` varchar(10),
                        `last_access_date` datetime,
                        `first_create_date` datetime,
                        `last_update_date` datetime
);

CREATE TABLE `host` (
                        `id` bigint auto_increment primary key,
                        `photo_url` varchar(200),
                        `description` varchar(200),
                        `user_id` varchar(200)
);

CREATE TABLE `room_reservation` (
                                    `id` bigint auto_increment primary key,
                                    `stay_start_date` datetime,
                                    `stay_end_date` datetime,
                                    `guest_people_cnt` int,
                                    `reservation_regist_time` datetime,
                                    `reservation_accept_time` datetime,
                                    `reservation_status` varchar(10),
                                    `guest_id` bigint,
                                    `room_id` bigint
);

CREATE TABLE `room_category_code` (
                                      `id` bigint auto_increment primary key,
                                      `category_name` varchar(50)
);

CREATE TABLE `room_category` (
                                 `id` bigint auto_increment primary key,
                                 `order` int,
                                 `category_id` bigint,
                                 `room_id` bigint
);

CREATE TABLE `room_review` (
                               `id` bigint auto_increment primary key,
                               `review_title` varchar(100),
                               `review_content` varchar(1000),
                               `stay_start_date` datetime,
                               `stay_end_date` datetime,
                               `first_create_date` datetime,
                               `last_update_date` datetime,
                               `user_id` bigint,
                               `room_id` bigint
);

CREATE TABLE `room_photo` (
                              `id` bigint auto_increment primary key,
                              `photo_url` varchar(200),
                              `order` int,
                              `room_id` bigint
);

CREATE TABLE `room_wish` (
                             `id` bigint auto_increment primary key,
                             `first_create_date` datetime,
                             `user_id` bigint,
                             `room_id` bigint
);

CREATE TABLE `host_address` (
                                `id` bigint auto_increment primary key,
                                `address` varchar(200),
                                `host_id` bigint
);

CREATE TABLE `host_room_address` (
                                     `host_room_address_id` bigint auto_increment primary key,
                                     `room_id` bigint
);

CREATE TABLE `room_address_facility` (
                                         `id` bigint auto_increment primary key,
                                         `room_address_id` bigint,
                                         `facility_id` varchar(50)
);

