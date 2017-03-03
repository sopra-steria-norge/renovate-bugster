create table ODDS (
  id SERIAL PRIMARY KEY,
  match_id SERIAL REFERENCES match(id),
  result VARCHAR2(1) NOT NULL,
  value DECIMAL,
  timestamped_at TIMESTAMP
);