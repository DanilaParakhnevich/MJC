create table if not exists gift_certificate (id int primary key,
                               name varchar (50),
                               description varchar (250),
                               price money,
                               duration int,
                               create_date timestamp,
                               last_update_date timestamp);

create table if not exists certificate_by_tag (id_certificate int references gift_certificate(id) not null,
                                id_tag int references tag (id) not null);

create table if not exists tag (id int primary key,
                    name varchar (35));