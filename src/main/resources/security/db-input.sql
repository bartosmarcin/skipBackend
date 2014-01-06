insert into users (username, password, enabled) values
('admin', 'admin$p', true),
('user', 'user$p', true),
('yourmom', 'yourmom$p', true);

insert into authorities (username, authority) values
('admin', 'ROLE_ADMIN'),
('user', 'ROLE_USER'),
('yourmom', 'ROLE_USER');
