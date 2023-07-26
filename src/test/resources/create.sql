create table author (id varchar(255) not null, name varchar(255) not null, user_id varchar(255) not null, primary key (id));
create table book (id varchar(255) not null, file_id uuid, page int4 not null, title varchar(255) not null, author_id varchar(255), primary key (id));
create table lib_user (id varchar(255) not null, login varchar(255) not null, password bytea not null, primary key (id));
create table text_file (id varchar(255) not null, book_id uuid, content bytea, primary key (id));
alter table if exists author add constraint author_unique_index unique (user_id, name);
alter table if exists book add constraint book_unique unique (author_id, title);
alter table if exists lib_user add constraint UK_on6iup30nxmxd2xg5kmijpg1o unique (login);
alter table if exists author add constraint FKq8m08c6cyrkuhf2jkvkjv3io8 foreign key (user_id) references lib_user;
alter table if exists book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author;
