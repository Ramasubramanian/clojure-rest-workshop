CREATE TABLE IF NOT EXISTS lead (
   lead_id serial PRIMARY KEY,
   first_name varchar(255),
   last_name varchar(255),
   dob varchar(10),
   ssn varchar(10),
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL
);