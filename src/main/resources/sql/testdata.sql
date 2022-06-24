truncate airjnc.user;

insert into airjnc.user(id, email, password, name)
    value (1, "test@naver.com", 1234, "창훈");

show create table airjnc.user;

select *
from user;
