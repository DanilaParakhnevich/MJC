drop table certificate_by_tag;
drop table gift_certificate;

create table if not exists gift_certificate (id bigserial primary key,
                               name varchar (50),
                               description varchar (250),
                               price money,
                               duration int,
                               create_date timestamp,
                               last_update_date timestamp);

drop table tag;

create table if not exists tag (id bigserial primary key,
                    name varchar (35));

create table if not exists certificate_by_tag (id_certificate int references gift_certificate(id) not null,
                                               id_tag int references tag (id) not null);