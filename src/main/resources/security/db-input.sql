insert into users (username, password, enabled, entity) values
('master', 'master$p', true, 0),
('admin', 'admin$p', true, 0),
('user', 'user$p', true, 1),
('driver1', '1234', true, 2),
('driver2', '4321', true, 3);

insert into authorities (username, authority) values
('master', 'ROLE_MASTER'),
('admin', 'ROLE_ADMIN'),
('user', 'ROLE_USER'),
('driver1', 'ROLE_USER'),
('driver2', 'ROLE_USER');
