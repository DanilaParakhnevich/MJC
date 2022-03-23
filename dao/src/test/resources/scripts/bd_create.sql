create sequence public.hibernate_sequence;

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