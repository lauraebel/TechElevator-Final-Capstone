BEGIN TRANSACTION;

DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS tools;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS tool_category;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS user_cart;

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

CREATE TABLE user_cart (
  user_id int PRIMARY KEY,
  tool0 int,
  tool1 int,
  tool2 int,
  tool3 int,
  tool4 int,
  tool5 int,
  tool6 int,
  tool7 int,
  tool8 int,
  tool9 int,

  CONSTRAINT fk_uc_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_uc_tool0 FOREIGN KEY (tool0) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool1 FOREIGN KEY (tool1) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool2 FOREIGN KEY (tool2) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool3 FOREIGN KEY (tool3) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool4 FOREIGN KEY (tool4) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool5 FOREIGN KEY (tool5) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool6 FOREIGN KEY (tool6) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool7 FOREIGN KEY (tool7) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool8 FOREIGN KEY (tool8) REFERENCES tools(id),
  CONSTRAINT fk_uc_tool9 FOREIGN KEY (tool9) REFERENCES tools(id)
);

COMMIT TRANSACTION;
