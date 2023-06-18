create table pictures
(
    id                  bigserial primary key,
    file_name           varchar(100) unique,
    bytes               bytea,
    content_type        varchar(50)
);

--constraint check_length check (octet_length bytes <= 5 * 1024 * 1024)