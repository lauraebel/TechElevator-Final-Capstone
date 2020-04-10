BEGIN TRANSACTION;

DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS tools;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS tool_category;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS cart_items;

CREATE TABLE roles (
  id serial PRIMARY KEY,
  name varchar(15)
);

CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role int NOT NULL default(1),
  first_name varchar(30) NOT NULL,
  last_name varchar(30) NOT NULL,
  license_number varchar(10) NOT NULL,

  CONSTRAINT fk_role_id FOREIGN KEY (role) REFERENCES roles (id)
);

CREATE TABLE brands (
  id serial PRIMARY KEY,
  name varchar(25) NOT NULL
);

CREATE TABLE tools (
  id serial PRIMARY KEY,
  name varchar(25) NOT NULL,
  description varchar(255),
  img_name varchar(25),
  brand_id int NOT NULL,

  CONSTRAINT fk_t_brand_id FOREIGN KEY (brand_id) references brands(id)
  );

CREATE TABLE category (
  id serial PRIMARY KEY,
  name varchar(25) NOT NULL
);

CREATE TABLE tool_category (
  tool_id int,
  category_id int,

  PRIMARY KEY (tool_id, category_id),
  CONSTRAINT fk_tc_tool_id FOREIGN KEY (tool_id) REFERENCES tools(id),
  CONSTRAINT fk_tc_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE loans (
  id serial PRIMARY KEY,
  user_id int NOT NULL,
  tool_id int NOT NULL, 
  loaned_on DATE NOT NULL,
  due_on DATE NOT NULL,
  returned_on DATE,

  CONSTRAINT fk_l_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_l_tool_id FOREIGN KEY (tool_id) REFERENCES tools(id)
);

CREATE TABLE cart_items (
  id serial PRIMARY KEY,
  user_id int NOT NULL,
  tool_id int NOT NULL,
  
  CONSTRAINT fk_ci_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_ci_tool_id FOREIGN KEY (tool_id) REFERENCES tools(id)
);

COMMIT TRANSACTION;
