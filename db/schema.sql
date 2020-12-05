create table sites (
id serial primary key,
login varchar(20) unique,
password varchar(100),
registration varchar(100)
);
create table codes (
id serial primary key,
code varchar (100)
);
create table urls (
id serial primary key,
url varchar (100),
total int,
codes_id int not null unique references codes(id)
);
create function getUrlByIdAndIncrementTotal(in id int, out url varchar)
language sql
as $$
update urls set  total = total + 1  where codes_id = id ;
select url from urls where codes_id = id;
  $$;