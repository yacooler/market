create table product(
    id bigserial primary key,
    title varchar(255) not null,
    price numeric(12,2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into product(title, price)
values
('CPU AMD Ryzen 7', 17300),
('CPU AMD Ryzen 5', 12400),
('CPU Intel Celeron', 17300),
('CPU Intel Core i3', 24900),
('CPU Intel Core i5', 31200),
('CPU Intel XEON 4 ядра', 38100),
('CPU Intel XEON 6 ядер', 42100),
('CPU Intel XEON 12 ядер', 75600),
('Motherboard Asus AMD', 5900),
('Motherboard Gigabyte AMD', 8300),
('Motherboard Asus Intel', 6300),
('Motherboard Noname Intel XEON', 7200),
('RAM DDR3 4*2 GB', 4300),
('RAM DDR3 8*2 GB', 9200),
('RAM DDR4 8*2 GB', 12100),
('RAM DDR4 16*2 GB', 17300),
('Palit RTX 3060', 57200),
('MSI RTX 3060ti', 63600),
('Gigabyte RTX 3070', 65200),
('Gigabyte RTX 3080', 102000),
('HDD 1TB', 4970),
('HDD 3TB', 8700),
('SSD 510GB', 3700),
('SSD 1TB', 6200)
;

create table users (
   id                      bigserial primary key,
   username                varchar(30) not null unique,
   password                varchar(80) not null,
   email                   varchar(50) unique,
   created_at              timestamp default current_timestamp,
   updated_at              timestamp default current_timestamp
);

create table roles (
   id                      bigserial primary key,
   name                    varchar(50) not null unique,
   created_at              timestamp default current_timestamp,
   updated_at              timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id                 bigint not null references users (id),
    role_id                 bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values
    ('ROLE_USER'),
    ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
    ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);


create table orders(
    id          bigserial,
    user_id     bigint  references users(id),
    total_items integer not null,
    total_price numeric(12,2) not null,
    delivery_address varchar(255) null,

    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp,

    primary key (id)
);

create table order_item(
    id          bigserial,
    order_id    bigint references orders(id),
    product_id  bigint references product(id),
    products_count integer not null,
    products_price numeric(12,2) not null,

    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp,

    primary key (id)
);