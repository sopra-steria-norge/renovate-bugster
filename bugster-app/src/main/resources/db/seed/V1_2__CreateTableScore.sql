create table Score (
  id SERIAL PRIMARY KEY,
  home INT NOT NULL,
  away INT NOT NULL,
  home_extratime INT,
  away_extratime INT,
  home_penalties INT,
  away_penalties INT
);