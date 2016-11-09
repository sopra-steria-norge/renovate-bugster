CREATE TYPE matchstatus AS ENUM ('scheduled', 'onGoing', 'finished');
create table Match (
  id SERIAL PRIMARY KEY,
  home_team SERIAL REFERENCES Team(id),
  away_team SERIAL REFERENCES Team(id),
  score SERIAL REFERENCES Score(id),
  status matchstatus NOT NULL,
  kickoff TIMESTAMP NOT NULL
);