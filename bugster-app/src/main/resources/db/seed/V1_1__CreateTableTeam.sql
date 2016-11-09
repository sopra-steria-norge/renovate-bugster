create table Team (
  id SERIAL PRIMARY KEY,
  name VARCHAR2(50) NOT NULL,
  short_name VARCHAR2(5) NOT NULL,
  active BOOLEAN DEFAULT TRUE
);