insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('jumping', 'jumping', 10, 10, '2022-03-13T01:00Z', '2022-03-15T00:00Z');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('swimming', 'swimming', 33, 35, '2022-03-12T02:00Z', '2022-03-15T00:00Z');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('basketball', 'basketball', 23, 35, '2022-03-15T00:00Z', '2022-03-15T00:00Z');

insert into tag (name) values ('jumping');
insert into tag (name) values ('fun');
insert into tag (name) values ('sport');
insert into tag (name) values ('clear');

insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)
values (2, 3);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)
values (2, 4);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)
values (3, 3);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)
values (2, 2);

insert into "user" (mail, nickname, password, balance)
values ('123@mail.ru', '123', '123', 100);
insert into "user" (mail, nickname, password, balance)
values ('321@mail.ru', '321', '321', 15.3);
insert into "user" (mail, nickname, password, balance)
values ('log@mail.ru', 'logger', 'logger22', 32.4);
insert into "user" (mail, nickname, password, balance)
values ('locdog@mail.ru', 'loci', 'locdog', 5.3);


select c.id as id, c.name as name, c.description as description, c.price as price,
            c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date, t.id as tag_id,
            t.name as tag_name from gift_certificate c left join certificate_by_tag
            on c.id = certificate_by_tag.id_certificate left join tag t on certificate_by_tag.id_tag = t.id
            order by c.id limit 10 offset 0


