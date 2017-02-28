create table Match (
  id SERIAL PRIMARY KEY,
  home_team SERIAL REFERENCES Team(id),
  away_team SERIAL REFERENCES Team(id),
  score SERIAL REFERENCES Score(id),
  status VARCHAR2(20) NOT NULL,
  start_date VARCHAR2(50) NOT NULL,
  CONSTRAINT match_uniqueness UNIQUE (home_team, away_team, start_date)
);