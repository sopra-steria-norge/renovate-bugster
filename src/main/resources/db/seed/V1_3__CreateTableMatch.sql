create table Match (
  id SERIAL PRIMARY KEY,
  home_team VARCHAR(100) NOT NULL,
  away_team VARCHAR(100) NOT NULL,
  score SERIAL REFERENCES Score(id),
  status VARCHAR2(20) NOT NULL,
  start_date TIMESTAMP NOT NULL,
  CONSTRAINT match_uniqueness UNIQUE (home_team, away_team, start_date)
);