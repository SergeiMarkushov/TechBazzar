create table logo
(
    id                  bigserial primary key,
    name                varchar(100),
    original_file_name  varchar(255),
    size                bigint,
    content_type        varchar(255),
    bytes               bytea
);

create table organizations
(
    id          bigserial primary key,
    title       varchar(100),
    description varchar(1000),
    owner       varchar(100),
    is_active   boolean,
    logo_id     bigint references logo(id)
);