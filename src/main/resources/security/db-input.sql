insert into users (username, password, enabled) values
('admin', 'admin$p', true),
('user', 'user$p', true),
('driver1', '1234', true),
('driver2', '4321', true);

insert into authorities (username, authority) values
('admin', 'ROLE_ADMIN'),
('user', 'ROLE_USER'),
('driver1', 'ROLE_USER'),
('driver2', 'ROLE_USER');
