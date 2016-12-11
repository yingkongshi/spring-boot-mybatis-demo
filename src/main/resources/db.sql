USE test;
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
  user_id varchar(32) NOT NULL,
  user_name varchar(30) NOT NULL,
  user_password varchar(30) NOT NULL,
  user_age int DEFAULT 0,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;