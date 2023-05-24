create table notifications
(
    id          bigserial primary key,
    title       varchar(255),
    created_at  timestamp default current_timestamp,
    content     varchar(1000),
    send_to     varchar(255)
);