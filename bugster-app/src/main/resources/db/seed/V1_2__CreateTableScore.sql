create table Score (
  id SERIAL PRIMARY KEY,
  home_full INT NOT NULL,
  away_full INT NOT NULL,
  home_extra INT NOT NULL,
  away_extra INT NOT NULL,
  home_penalties INT NOT NULL,
  away_penalties INT NOT NULL,
  extra_time BOOLEAN DEFAULT FALSE,
  penalties BOOLEAN DEFAULT FALSE
);