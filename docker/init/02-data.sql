# USER
INSERT INTO user (id, email, password, name, gender, address, birthdate, created_at)
VALUES (1, 'test@naver.com', 'q1w2e3r4t5!', 'testUser', 'MALE', '서울 강남구', '2020-01-01', '2022-01-01');
# Host
INSERT INTO host (id, photo_url, description, user_id)
VALUES (1, 'photo', 'description', 1);
# Category
INSERT INTO room_category
VALUES (1, 'TEST_CATEGORY-1');
INSERT INTO room_category
VALUES (2, 'TEST_CATEGORY-2');
# Room
INSERT INTO room (id, title, description, pricePerNight, bed_count, bedroom_count, bathroom_count, room_count,
                  max_guest_count,
                  min_number_of_nights, max_number_of_nights, check_in, check_out, address, status, created_at,
                  updated_at, deleted_at, host_id, room_category_id)
VALUES (1, 'title', 'description',
        1000, 1, 2, 3, 4, 5, 2, 4, 12, 14,
        'address', 'IN_OPERATION', NOW(), NOW(), NULL, 1, 1);
INSERT INTO room (id, title, description, pricePerNight, bed_count, bedroom_count, bathroom_count, room_count,
                  max_guest_count,
                  min_number_of_nights, max_number_of_nights, check_in, check_out, address, status, created_at,
                  updated_at, deleted_at, host_id, room_category_id)
VALUES (2, 'title', 'description',
        1000, 1, 2, 3, 4, 5, 2, 4, 12, 14,
        'address', 'IN_OPERATION', NOW(), NOW(), NULL, 1, 1);
# Room Photo
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (1, 'url-1', 1, 1);
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (2, 'url-2', 2, 1);
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (3, 'url-3', 3, 1);
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (4, 'url-4', 4, 1);
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (5, 'url-5', 1, 2);
INSERT INTO room_photo (id, url, `order`, room_id)
VALUES (6, 'url-6', 2, 2);
# Room Review
INSERT INTO room_review (id, content, `like`, created_at, updated_at, deleted_at, user_id, room_id)
VALUES (1, 'content-1', 2, NOW(), NOW(), NULL, 1, 1);
INSERT INTO room_review
VALUES (2, 'content-2', 1, NOW(), NOW(), NULL, 1, 1);
INSERT INTO room_review
VALUES (3, 'content-3', 3, NOW(), NOW(), NULL, 1, 1);
# Wish Room
INSERT INTO wish_room (id, user_id, room_id)
VALUES (1, 1, 1);
