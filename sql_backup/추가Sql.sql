
use ssafytrip;

create table users (
	id varchar(100) primary key,
    pw varchar(100) not null
);

Alter table attraction_info add column recommend INT(10) default 0 after gugun_code;

create table myplace (
	content_id char(36) primary key default (UUID()),
    user_id char(36) not null,
    title char(36),
    content_type char(18),
    info char(100),
    mapx char(50),
    mapy char(50)
);

Alter table users add column email char(50)  after pw;
ALTER TABLE users ADD name VARCHAR(16);

