create table users
(
    id          bigserial primary key,
    username    varchar(36) unique,
    full_name   varchar(36),
    balance     numeric(10, 2),
    is_active   boolean
);

insert into users (username, full_name, balance, is_active) values
    ('n.v.bekhter@mail.ru', 'Nikolay Bekhter', 100000, true),
    ('nik.noreply.b@mail.ru', 'Nikolay Bekhter', 100000, true);