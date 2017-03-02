create table BET (
  id SERIAL PRIMARY KEY,
  match_id SERIAL REFERENCES match(id),
  result VARCHAR2(1) NOT NULL,
  value SERIAL,
  timestamped_at TIMESTAMP
);