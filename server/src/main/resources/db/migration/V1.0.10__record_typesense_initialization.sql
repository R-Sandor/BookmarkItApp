CREATE TABLE if NOT EXISTS typesense_intialization (
  id serial not null primary key,
  script_name varchar(64),
  path varchar(64),
  initialized boolean,
  init_date timestamp(6)
);
