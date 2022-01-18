create table gift_certificate (id int primary key,
                               name varchar (50),
                               description varchar (250),
                               price money,
                               duration int,
                               create_date timestamp,
                               last_update_date timestamp)

create table tag (id int primary key REFERENCES gift_certificate (id),
name varchar (35))