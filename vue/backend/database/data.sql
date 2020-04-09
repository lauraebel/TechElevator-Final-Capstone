INSERT INTO roles (id, name) VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, name) VALUES (DEFAULT, 'user');

INSERT INTO users (id, username, password, salt, role, first_name, last_name, license_number) 
    VALUES (DEFAULT, 'katherinelillemoen', 'password', 'salt', (SELECT id FROM roles WHERE name = 'user'), 'katherine', 'lillemoen', '09281999');
INSERT INTO users (id, username, password, salt, role, first_name, last_name, license_number)
    VALUES (DEFAULT, 'lauraebel', 'password1', 'salt', (SELECT id FROM roles WHERE name = 'user'), 'laura', 'ebel', '10291991');
INSERT INTO users (id, username, password, salt, role, first_name, last_name, license_number)    
    VALUES (DEFAULT, 'marthaweisheimer', 'password2', 'salt', (SELECT id FROM roles WHERE name = 'user'), 'martha', 'weisheimer', '05301985');
INSERT INTO users (id, username, password, salt, role, first_name, last_name, license_number)    
    VALUES (DEFAULT, 'brianlauvray', 'password3', 'salt', (SELECT id FROM roles WHERE name = 'admin'), 'brian', 'lauvray', '01011980');
INSERT INTO users (id, username, password, salt, role, first_name, last_name, license_number)    
    VALUES (DEFAULT, 'rachellerauth', 'password4', 'salt', (SELECT id FROM roles WHERE name = 'admin'), 'rachelle', 'rauth', '01011990');

INSERT INTO carts (user_id) VALUES ((SELECT id FROM users WHERE username = 'katherinelillemoen'));
INSERT INTO carts (user_id) VALUES ((SELECT id FROM users WHERE username = 'lauraebel'));
INSERT INTO carts (user_id) VALUES ((SELECT id FROM users WHERE username = 'marthaweisheimer'));
INSERT INTO carts (user_id) VALUES ((SELECT id FROM users WHERE username = 'brianlauvray'));
INSERT INTO carts (user_id) VALUES ((SELECT id FROM users WHERE username = 'rachellerauth'));

INSERT INTO brands (id, name) 
    VALUES (DEFAULT, 'black & decker');
INSERT INTO brands (id, name) 
    VALUES (DEFAULT, 'dewalt');
INSERT INTO brands (id, name) 
    VALUES (DEFAULT, 'husky');
INSERT INTO brands (id, name) 
    VALUES (DEFAULT, 'stanley');

INSERT INTO tools (id, name, description, img_name, brand_id) 
    VALUES (DEFAULT, 'power drill', 'a really cool drill', 'power-drill.jpg', (SELECT id FROM brands WHERE name = 'black & decker'));
INSERT INTO tools (id, name, description, img_name, brand_id) 
    VALUES (DEFAULT, 'hammer', 'a really cool hammer', 'hammer.jpg', (SELECT id FROM brands WHERE name = 'husky'));
INSERT INTO tools (id, name, description, img_name, brand_id) 
    VALUES (DEFAULT, 'screwdriver', 'a really cool screwdriver', 'screwdriver.jpg', (SELECT id FROM brands WHERE name = 'stanley'));
INSERT INTO tools (id, name, description, img_name, brand_id) 
    VALUES (DEFAULT, 'shovel', 'a really cool shovel', 'shovel.jpg', (SELECT id FROM brands WHERE name = 'dewalt'));
INSERT INTO tools (id, name, description, img_name, brand_id) 
    VALUES (DEFAULT, 'socket wrench', 'a really cool socket wrench', 'socket-wrench.jpg', (SELECT id FROM brands WHERE name = 'black & decker'));

INSERT INTO category (id, name) 
    VALUES (DEFAULT, 'power tool');
INSERT INTO category (id, name) 
    VALUES (DEFAULT, 'hand tool');
INSERT INTO category (id, name) 
    VALUES (DEFAULT, 'garden');
INSERT INTO category (id, name) 
    VALUES (DEFAULT, 'automotive');

INSERT INTO tool_category (tool_id, category_id) 
    VALUES ((SELECT id FROM tools WHERE name = 'power drill'), (SELECT id FROM category WHERE name = 'power tool'));
INSERT INTO tool_category (tool_id, category_id) 
    VALUES ((SELECT id FROM tools WHERE name = 'hammer'), (SELECT id FROM category WHERE name = 'hand tool'));
INSERT INTO tool_category (tool_id, category_id) 
    VALUES ((SELECT id FROM tools WHERE name = 'screwdriver'), (SELECT id FROM category WHERE name = 'hand tool'));
INSERT INTO tool_category (tool_id, category_id) 
    VALUES ((SELECT id FROM tools WHERE name = 'shovel'), (SELECT id FROM category WHERE name = 'garden'));
INSERT INTO tool_category (tool_id, category_id) 
    VALUES ((SELECT id FROM tools WHERE name = 'socket wrench'), (SELECT id FROM category WHERE name = 'automotive'));
    
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'katherinelillemoen')), 1);
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'katherinelillemoen')), 2);

INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'lauraebel')), 3);
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'lauraebel')), 4);

INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'marthaweisheimer')), 1);
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'marthaweisheimer')), 2);

INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'brianlauvray')), 3);
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'brianlauvray')), 4);

INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'rachellerauth')), 5);
INSERT INTO cart_items (cart_id, tool_id) VALUES ((SELECT id FROM carts WHERE user_id = (SELECT id FROM users WHERE username = 'rachellerauth')), 1);
    
-- INSERT INTO loans (id, user_id, tool_id, loaned_on, due_on, returned_on) 
--     VALUES ();

-- INSERT INTO carts (user_id, tool0, tool1) 
--     VALUES ();

-- Password for this user is 'greatwall'
-- INSERT INTO users ('username', 'password', 'salt', 'role') VALUES
-- ('user',
-- 'FjZDm+sndmsdEDwNtfr6NA==',
-- 'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=',
-- 'user');

