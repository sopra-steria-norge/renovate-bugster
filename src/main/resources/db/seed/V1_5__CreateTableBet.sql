create table BET (
  id SERIAL PRIMARY KEY,
  odds_id SERIAL REFERENCES match(id),
  user_id SERIAL REFERENCES user(id),
  amount SERIAL NOT NULL
);