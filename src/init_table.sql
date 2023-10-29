drop table if exists employee;
-- 
--
create table employee (
	id int not null auto_increment, 
	name varchar(50) not null, 
	email varchar(50), 
	mobile varchar(50),
	gender varchar(50),
	dob date,
	city varchar(50),
	primary key (id));
	
create unique index idx_name on employee (name);