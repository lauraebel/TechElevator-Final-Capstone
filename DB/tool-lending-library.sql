DROP TABLE IF EXISTS tool_category;
DROP TABLE IF EXISTS reservation_log;
DROP TABLE IF EXISTS system_user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS tool;
DROP TABLE IF EXISTS brand;

CREATE TABLE role (
id serial primary key,
role_name varchar(35) NOT NULL
);

CREATE TABLE category (
id serial primary key,
category_name varchar(35) NOT NULL
);

CREATE TABLE brand (
id serial primary key,
brand_name varchar(35) NOT NULL
);

CREATE TABLE tool (
id serial primary key,
tool_name varchar(60) NOT NULL,
tool_description varchar(255),
brand_id int,

constraint fk_brand_id foreign key (brand_id) references brand(brand_id)
);

CREATE TABLE system_user (
id serial primary key,
first_name varchar(35) NOT NULL,
last_name varchar(35) NOT NULL,
email varchar(100) NOT NULL,
user_password varchar (100) NOT NULL,
drivers_license varchar(35) NOT NULL,
role_id int,

constraint fk_role_id foreign key (role_id) references role(id)
);

CREATE TABLE reservation_log (
id serial primary key,
user_id int,
tool_id int,
date_loaned date,
date_due date,
date_returned date,

constraint fk_system_user_id foreign key (system_user_id) references system_user(id),
constraint fk_tool_id foreign key (tool_id) references tool(tool_id)
);

CREATE TABLE tool_category (
tool_id int,
category_id int,

constraint fk_tool_tool_id foreign key (tool_id) references tool(id),
constraint fk_category_id foreign key (category_id) references category(id),
PRIMARY KEY(tool_id, category_id)
);