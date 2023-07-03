
INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$p0RQrp/woH48GFR7GuvH4OZzlgWi4b8rbUCqZYdDcD9wuAkMpwFnu','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$bXv9WWXm7gQ3apxlgRFJAe/IRZ9.DHxFRWuj46jVfwHO3jnJJyDiC', 'admin@test.nl', TRUE);
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO products (category, description, product_name, unit_price) VALUES
('ELECTRONICS', 'Samsung', 'Laptop', 50),
('LADIES_CLOTHING', 'heel mooi', 'Jas', 11);
INSERT INTO orders (total_amount, user_id) VALUES (5000, 1);
INSERT INTO orderline (quantity, sub_total, order_id, product_id)
VALUES
(50, 2500, 1, 1),
(50, 2500, 1, 2);
INSERT INTO invoice (invoice_date, total_amount, user_id, order_id) VALUES ('2023-06-29', 2500, 1, 1);