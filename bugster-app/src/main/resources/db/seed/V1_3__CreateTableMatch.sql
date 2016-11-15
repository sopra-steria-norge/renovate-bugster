create table Match (
  id SERIAL PRIMARY KEY,
  home_team SERIAL REFERENCES Team(id),
  away_team SERIAL REFERENCES Team(id),
  score SERIAL REFERENCES Score(id),
  status VARCHAR2(10) NOT NULL
);