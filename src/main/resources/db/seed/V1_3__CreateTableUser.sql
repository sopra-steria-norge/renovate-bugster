create table User (
  id SERIAL PRIMARY KEY,
  username VARCHAR2(50) UNIQUE NOT NULL,
  password VARCHAR2(150) UNIQUE NOT NULL,
  name VARCHAR2(50) NOT NULL
);

insert into USER (id, username, password, name) values (1, 'teide', 'password01', 'Tor Erik Eide');