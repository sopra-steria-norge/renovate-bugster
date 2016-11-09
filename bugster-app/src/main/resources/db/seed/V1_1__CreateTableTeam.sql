create table Team (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  short_name TEXT NOT NULL,
  active BOOLEAN DEFAULT TRUE
);