# 뱅크 테이블 설계

```sql
create table user_tb
(
    id         integer generated by default as identity,
    created_at timestamp(6),
    password   varchar(12)  not null,
    username   varchar(12)  not null unique,
    fullname   varchar(255) not null,
    primary key (id)
);

create table account_tb
(
    balance    integer,
    number     integer not null,
    user_id    integer,
    created_at timestamp(6),
    password   varchar(255),
    primary key (number)
);

create table history_tb
(
    amount           integer,
    withdraw_balance integer,
    deposit_number   integer,
    id               integer generated by default as identity,
    withdraw_number  integer,
    created_at       timestamp(6),
    primary key (id)
);
```