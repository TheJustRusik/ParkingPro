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

create table parking_type (
    id bigserial primary key,
    type varchar(255) not null unique
);

create table parking (
    id bigserial primary key,
    name varchar(255) not null unique,
    lat double precision not null,
    lon double precision not null,
    price double precision check ( price >= 0 ) not null,
    type_id bigint references parking_type,
    max_capacity int,
    current_workload int
);

create table parking_points (
    parking_id bigint references parking,
    lat double precision not null,
    lon double precision not null,
    primary key (lon, lat)
);

create table parking_likes (
    parking_id bigint references parking,
    user_id bigint references users,
    is_dislike boolean not null default true,
    primary key (parking_id, user_id)
);

create table parking_reviews (
    parking_id bigint references parking,
    user_id bigint references users,
    stars smallint check ( stars >= 1 and stars <= 5),
    review text,
    primary key (parking_id, user_id)
);

insert into parking_type values
                             (1, 'Underground'),
                             (2, 'With roof'),
                             (3, 'Outdoor'),
                             (4, 'Multi storey'),
                             (5, 'By the road');

insert into roles values
                      (1, 'User'),
                      (2, 'Parking owner'),
                      (3, 'Moderator'),
                      (4, 'Admin')