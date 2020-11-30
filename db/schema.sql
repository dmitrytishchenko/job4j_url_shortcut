create table sites (
id serial primary key,
login varchar(20) unique,
password varchar(100),
registration varchar(100)
);
create table urls (
id serial primary key,
url varchar (100),
total int,
codes_id int not null unique references codes(id)
);
create table codes (
id serial primary key,
code varchar (100)
);