DROP DATABASE IF EXISTS DBUNIT;
CREATE DATABASE IF NOT EXISTS DBUNIT;
USE DBUNIT;

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
                         id int(11) NOT NULL,
                         nombre varchar(45) DEFAULT NULL,
                         apellido varchar(45) DEFAULT NULL,
                         direccion varchar(45) DEFAULT NULL,
                         ciudad varchar(45) DEFAULT NULL,
                         PRIMARY KEY (id)
);