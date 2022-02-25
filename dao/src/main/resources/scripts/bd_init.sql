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

insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (2, 3);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (2, 4);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (3, 3);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (2, 2);

select * from CERTIFICATE_BY_TAG;
select * from TAG;
select * from GIFT_CERTIFICATE;
