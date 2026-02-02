create table carts
(
    cart_id             bigint auto_increment,
    user_id             bigint not null,
    total_items         int,
    total_price         decimal(10, 2),
    currency            varchar(25),
    created_at          datetime not null,
    created_by          varchar(255) not null,
    updated_at          datetime null,
    updated_by          varchar(255) null,
    constraint products_pk
        primary key (cart_id)
);

create table cart_items
(
    item_id             int auto_increment,
    cart_id             bigint,
    product_id          bigint,
    quantity            int not null,
    price               decimal(10, 2),
    created_at          datetime not null,
    created_by          varchar(255) not null,
    updated_at          datetime null,
    updated_by          varchar(255) null,
    constraint cart_items_pk
        primary key (item_id),
    foreign key (cart_id) references carts(cart_id)
);