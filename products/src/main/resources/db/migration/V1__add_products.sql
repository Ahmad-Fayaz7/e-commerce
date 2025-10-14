create table products
(
    id                  bigint auto_increment,
    description         varchar(512) null,
    price               decimal      not null,
    currency            varchar(255) not null,
    availability_status varchar(255) not null,
    category            varchar(255) null,
    brand               varchar(255) null,
    name                varchar(255) not null,
    created_at          datetime     not null,
    created_by          varchar(255) not null,
    updated_at          datetime     null,
    updated_by          varchar(255) null,
    constraint products_pk
        primary key (id)
);
