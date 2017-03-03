create table User (
  id SERIAL PRIMARY KEY,
  username VARCHAR2(50) UNIQUE NOT NULL,
  balance SERIAL
);

insert into USER (id, username, balance) values (1, 'bruker', 5000);
insert into USER (id, username, balance) values (2, 'bruker2', 1000);