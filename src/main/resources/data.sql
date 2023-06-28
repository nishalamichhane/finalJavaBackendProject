
INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$p0RQrp/woH48GFR7GuvH4OZzlgWi4b8rbUCqZYdDcD9wuAkMpwFnu','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$bXv9WWXm7gQ3apxlgRFJAe/IRZ9.DHxFRWuj46jVfwHO3jnJJyDiC', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');