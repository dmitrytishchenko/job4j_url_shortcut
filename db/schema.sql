create table sites (
id serial primary key,
login varchar(20) unique,
password varchar(100),
registration varchar(100)
);