
INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$p0RQrp/woH48GFR7GuvH4OZzlgWi4b8rbUCqZYdDcD9wuAkMpwFnu','nishalamichhane9@gmail.com', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$bXv9WWXm7gQ3apxlgRFJAe/IRZ9.DHxFRWuj46jVfwHO3jnJJyDiC', 'nishalamichhane9@gmail.com', TRUE);
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO products (category, description, product_name, unit_price, image_data, name, type) VALUES
('ELECTRONICS', 'Samsung', 'Laptop', 50, '34058', 'jas.png', 'image/png'),
('LADIES_CLOTHING', 'heel mooi', 'Jas', 11, '34059', 'ring.png', 'image/png');
INSERT INTO orders (total_amount, username) VALUES (5000, 'user');
INSERT INTO orderlines (quantity, sub_total, order_id, product_id)
VALUES
(50, 2500, 1, 1),
(50, 2500, 1, 2);
INSERT INTO invoice (invoice_date, order_id) VALUES ('2023-06-29', 1);