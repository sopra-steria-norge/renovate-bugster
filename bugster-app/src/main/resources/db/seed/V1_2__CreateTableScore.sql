create table Score (
  id SERIAL PRIMARY KEY,
  home INT NOT NULL,
  away INT NOT NULL,
  home_penalties INT,
  away_penalties INT
);