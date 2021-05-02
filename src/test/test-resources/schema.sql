create table order_data
(
    order_number varchar2(255) not null,
    product varchar2(255) not null,
    primary key(order_number)
);

create table audit
(
    row_id varchar2(255) not null,
    entity_type varchar2(20) not null,
    entity_id varchar2(255) not null,
    event varchar2(20) not null,
    host_name varchar2(255) not null,
    create_date timestamp not null,
    primary key(row_id)
);