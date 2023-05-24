create table users
(
    id          bigserial primary key,
    email       varchar(255) unique,
    password    varchar(80) not null,
    username    varchar(36),
    balance     numeric(10, 2),
    is_active   boolean
);

create table roles
(
    id          bigserial primary key,
    name        varchar(15) not null
);

create table users_roles
(
    user_id     bigint not null references users(id),
    role_id     bigint not null references roles(id),
    primary key (user_id, role_id)
);

insert into users (email, password, username, balance, is_active) values
    ('n.v.bekhter@mail.ru', '$2a$12$8jJ2aWY1jYu2fUTib.Ovuu7uiiodaPzHHExOSP9Ykm.lafgse9gim', 'Kolya', 100000, true),
    ('nik.noreply.b@mail.ru', '$2a$12$8jJ2aWY1jYu2fUTib.Ovuu7uiiodaPzHHExOSP9Ykm.lafgse9gim', 'Kolya', 100000, true);

insert into roles (name) values
                             ('ROLE_USER'), ('ROLE_ADMIN');

insert into users_roles (user_id, role_id) values
                                               (1, 1),
                                               (1, 2),
                                               (2, 1),
                                               (2, 2);