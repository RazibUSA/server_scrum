insert into role (id, name) values(1, 'System Admin');
insert into role (id, name) values(2, 'Product Owner');
insert into role (id, name) values(3, 'Scrum Master');
insert into role (id, name) values(4, 'Developer');


insert into role (id, name) values(1, 'TODO');
insert into role (id, name) values(2, 'INPROGRESS');
insert into role (id, name) values(3, 'DONE');
insert into role (id, name) values(3, 'STOPPED');

insert into user (first_name, last_name, email, password, role_id, version) values ('admin', 'admin', 'admin@tt.com', 'admin', 1, 0);
insert into user (first_name, last_name, email, password, role_id, version) values ('product', 'owner', 'productowner@tt.com', 'po', 2, 0);
insert into user (first_name, last_name, email, password, role_id, version) values ('scrum', 'master', 'scrummaster@tt.com', 'sc', 3, 0);
insert into user (first_name, last_name, email, password, role_id, version) values ('developer', 'developer', 'developer@tt.com', 'po', 4, 0);


