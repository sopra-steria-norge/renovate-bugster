create table BET (
  id SERIAL PRIMARY KEY,
  match_id SERIAL REFERENCES match(id),
  user_id SERIAL REFERENCES user(id),
  result VARCHAR2(1) NOT NULL
);