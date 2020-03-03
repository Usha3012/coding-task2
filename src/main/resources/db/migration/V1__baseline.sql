create schema if not exists zenhome;
SET search_path = zenhome, pg_catalog;

CREATE TABLE IF NOT EXISTS zenhome.village(
id SERIAL PRIMARY KEY,
name VARCHAR(225)
);

CREATE TABLE IF NOT EXISTS zenhome.counter(
id SERIAL PRIMARY KEY,
village_id INTEGER references zenhome.village(id)
);

CREATE TABLE IF NOT EXISTS zenhome.consumption(
id SERIAL PRIMARY KEY,
counter_id INTEGER references zenhome.counter(id),
amount NUMERIC NOT NULL,
reported_at TIMESTAMP NOT NULL
);