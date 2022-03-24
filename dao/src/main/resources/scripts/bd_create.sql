drop table certificate_by_tag;
drop table tag;
drop table gift_certificate;
create table if not exists gift_certificate (id bigserial primary key,
                               name varchar (50),
                               description varchar (250),
                               price numeric,
                               duration bigint,
                               create_date timestamp,
                               last_update_date timestamp);


create table if not exists tag (id bigserial primary key,
                    name varchar (35));

create table if not exists certificate_by_tag (
    id_certificate bigint references gift_certificate(id) ,
    id_tag bigint references tag (id)
    );

create table if not exists "user" (id bigserial primary key,
    mail varchar(320),
    nickname varchar(24),
    password varchar(72),
    balance numeric
)