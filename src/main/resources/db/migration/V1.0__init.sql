create table users (
    id bigserial primary key,
    email varchar(128) not null unique,
    nickname varchar(128) not null unique,
    password varchar(80) not null
);