create table reviews
(
    id          bigserial primary key,
    mark        integer,
    review_text varchar(255),
    username    varchar(255)
);

create table discounts
(
    id          bigserial primary key,
    dis         integer,
    expiry_date timestamp not null,
    start_date  timestamp not null
);

create table products
(
    id                 bigserial primary key,
    description        varchar(1000),
    price              numeric(8, 2),
    quantity           integer,
    title              varchar(100),
    organization_title varchar(100),
    is_confirmed       boolean,
    discount_id        bigint references discounts(id),
    review_id          bigint references reviews(id)
);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric(8, 2),
    address     varchar(255),
    phone       varchar(255),
    status      boolean,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id          bigserial primary key,
    product_id  bigint not null references products(id),
    order_id    bigint not null references orders(id),
    quantity    int not null ,
    price_per_product numeric(8, 2) not null ,
    price        numeric(8, 2) not null ,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table purchase_history
(
    id              bigserial primary key,
    email           varchar(255),
    product_title   varchar(255),
    organization    varchar(255),
    quantity        int not null ,
    date_purchase   timestamp default current_timestamp
);