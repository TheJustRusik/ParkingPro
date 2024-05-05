create table users (
    id bigserial primary key,
    email varchar(128) not null unique,
    nickname varchar(128) not null unique,
    password varchar(80) not null
);

create table roles (
    id bigserial primary key,
    name varchar(16) unique not null
);

create table user_roles (
    user_id bigint references users,
    role_id bigint references roles,
    primary key (user_id, role_id)
);