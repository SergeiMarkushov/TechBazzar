create table pictures
(
    id                  bigserial primary key,
    file_name           varchar(100),
    bytes               bytea,
    content_type        varchar(50)
);