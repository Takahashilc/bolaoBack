
create table tb_user (
    id bigint primary key not null auto_increment,
    nickname varchar(30) not null,
    points int,
    email varchar(100) not null,
    password varchar(255) not null,
    is_first_login boolean,
    status varchar(30),
    activation_code varchar(6)
);

create table tb_user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references tb_user(id),
    foreign key (role_id) references tb_role(id)
);

create table tb_role (
    id bigint primary key not null auto_increment,
    authority varchar(30) not null
);

create table tb_log (
    id bigint primary key not null auto_increment,
    user_id bigint not null,
    action varchar(30) not null,
    foreign key (user_id) references tb_user(id)
);

create table tb_tipo (
    id bigint primary key not null auto_increment,
    user_id bigint not null,
    match_id bigint not null,
    result varchar(20) not null,
    foreign key (user_id) references tb_user(id),
    foreign key (match_id) references tb_match(id)
);

create table tb_group (
    id bigint primary key not null auto_increment,
    name char not null
);

create table tb_team (
    id bigint primary key not null auto_increment,
    name varchar(30) not null,
    acronym varchar(3) not null,
    group_id bigint not null,
    flag_url varchar(255) not null
);

create table tb_match (
    id bigint primary key not null auto_increment,
    result varchar(20) not null default 'NOT_PLAYED'
);

create table tb_tipo (
    id bigint primary key not null auto_increment,
    user_id bigint not null,
    match_id bigint not null,
    result varchar(20) not null,
    foreign key (user_id) references tb_user(id),
    foreign key (match_id) references tb_match(id)
);

create table tb_team_match (
    type varchar(5) not null,
    team_id bigint not null,
    match_id bigint not null,
    primary key (team_id, match_id),
    foreign key (team_id) references tb_team(id),
    foreign key (match_id) references tb_match(id)
);