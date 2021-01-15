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