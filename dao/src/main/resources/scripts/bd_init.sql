INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('jumping', 'jumping', 10, 10, '2022-03-15T12:00Z', '2022-03-15T12:00Z');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('swimming', 'swimming', 33, 35, '2022-03-15T13:00Z', '2022-03-15T14:00Z');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('basketball', 'basketball', 23, 35, '2022-03-15T15:00Z', '2022-03-15T15:00Z');

insert into tag (id, name) values (1, 'jumping');
insert into tag (id, name) values (2, 'fun');
insert into tag (id, name) values (3, 'sport');
insert into tag (id, name) values (4, 'clear');

insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG) values (1, 1);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (1, 2);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (2, 3);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (2, 4);
insert into CERTIFICATE_BY_TAG (ID_CERTIFICATE, ID_TAG)  values (3, 3);

select * from CERTIFICATE_BY_TAG;
select * from TAG;
select * from GIFT_CERTIFICATE;