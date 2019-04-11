CREATE TABLE test.ss_leaguer_info(
	id INT NOT NULL AUTO_INCREMENT,
	leaguer_name varchar(100) NULL,
	password varchar(100) NULL,
	is_super_user INT  NULL,
	recent_appointment_time TIMESTAMP NULL,
	recent_lease_time TIMESTAMP NULL,
	primary key(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;


CREATE TABLE test.ss_resource_info(
	id INT NOT NULL AUTO_INCREMENT,
	resource_name varchar(100) NULL,
	resource_desc varchar(100) NULL,
	appointment_time TIMESTAMP NULL,
	lease_time TIMESTAMP NULL,
	back_time TIMESTAMP NULL,
	state INT  NULL,
	user_id INT  NULL,
	primary key(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
