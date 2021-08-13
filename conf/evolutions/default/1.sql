# --- !Ups
create table people (
  id serial not null,
  name varchar(255) not null,
  mail varchar(255) not null,
  tel varchar(255),
  primary key(id)
);
insert into people values (default, 'taro', 'taro@yamada', '999-999');
insert into people values (default, 'hanako', 'hanako@flower', '888-888');
insert into people values (default, 'sachiko', 'sachiko@happy', '777-777');

# --- !Downs
drop table people