create database musica;
create user 'alumno'@'%' identified by 'alumno';
grant create, drop, delete, insert, select, update on * . * to 'alumno'@'%';
FLUSH PRIVILEGES;

use musica;
DROP TABLE IF EXISTS canciones;

CREATE TABLE IF NOT EXISTS canciones (
  id int(5) NOT NULL AUTO_INCREMENT,
  titulo varchar(50) DEFAULT NULL, 
  artista varchar(50) DEFAULT NULL,
  album varchar(50) DEFAULT NULL,
  anyo int(4) DEFAULT NULL,
  genero varchar(50) DEFAULT NULL,
  caratula longblob DEFAULT NULL,
  archivo longblob DEFAULT NULL, 
  PRIMARY KEY (id)
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;