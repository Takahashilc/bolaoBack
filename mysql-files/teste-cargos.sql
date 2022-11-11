insert into tb_user (nickname, email, password, is_first_login, status, points) values (
    'sonikzu',
    'bolaoworldcup2022@gmail.com',
    '123456789',
    true,
    'CONFIRMED',
    0
);

insert into tb_user (nickname, email, password, is_first_login, status, points) values (
    'ltakahashi',
    'ltakahashi@gmail.com',
    '$2a$10$hom3/vwQkOAOi4iCVsLfEeTPm1jStxHpctjuiDXLe0ULHJ5//xPBa',
    true,
    'CONFIRMED',
    0
);

insert into tb_user (nickname, email, password, is_first_login, status, points) values (
    'leandro',
    'leandrocosta@teste.com',
    '$2a$10$hom3/vwQkOAOi4iCVsLfEeTPm1jStxHpctjuiDXLe0ULHJ5//xPBa',
    false,
    'CONFIRMED',
    0
);

insert into tb_role (authority) values ('USUÁRIO');
insert into tb_role (authority) values ('ADMINISTRADOR');

insert into tb_user_role (user_id, role_id) values (1, 1);
insert into tb_user_role (user_id, role_id) values (1, 2);
insert into tb_user_role (user_id, role_id) values (2, 1);