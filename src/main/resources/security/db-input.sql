insert into users (username, password, enabled, entity) values
('master', 'master$p', true, 0);

insert into authorities (username, authority) values
('master', 'ROLE_MASTER');
