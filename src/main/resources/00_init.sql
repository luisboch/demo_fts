CREATE TABLE IF NOT EXISTS brand(
	id bigserial primary key,
	"name" character varying (100) not null
);

CREATE TABLE IF NOT EXISTS car(
	id bigserial primary key,
	"name" varchar (200) not null,
	brand_id int8 not null references brand(id)
);


CREATE TABLE IF NOT EXISTS cep(
    zip_code int8 primary key,
    address character varying (200),
    details character varying (200),
    neighborhood character varying (50),
    city_name character varying (100),
    state_code character varying (2),
    ibge_code int4,
    ddd int2
);

insert into brand(name) values ('Renault');
insert into brand(name) values ('Nissan');