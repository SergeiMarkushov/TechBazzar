create table users
(
    id          bigserial primary key,
    username    varchar(36) unique,
    balance     numeric(10, 2),
    is_active   boolean
);

insert into users (username, balance, is_active) values
    ('n.v.bekhter@mail.ru', 100000, true),
    ('nik.noreply.b@mail.ru', 100000, true);