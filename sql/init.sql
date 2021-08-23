create table story
(
    id int auto_increment
        primary key,
    text text null,
    create_time datetime null,
    addr varchar(255) null
);

create table story_pic
(
    id int auto_increment
        primary key,
    url varchar(1000) not null,
    story_id bigint not null
);

create table story_tag
(
    id int auto_increment
        primary key,
    title varchar(16) not null,
    story_id bigint not null
);

create table tag
(
    id int auto_increment
        primary key,
    title varchar(16) not null
);

