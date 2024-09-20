create table user
(	
	id int not null auto_increment,
    active  boolean not null,
    password varchar(20) not null,
    roles varchar(255),
    user_name varchar(20) not null,
    primary key (id)
);

insert into user (id, active, password, roles, user_name)
values (1, true, "Babubabu123**", "ROLE_USER", "animesh");

insert into user (active, password, roles, user_name)
values (true, "password", "ROLE_ADMIN", "admin");

select * from user;

rename table user to user;