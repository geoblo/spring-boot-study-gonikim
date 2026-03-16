INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK', TRUE);

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');