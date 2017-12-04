/*
	hostname = 127.0.0.1
	port = 3306
	db Name = chatDB
	username = "root";
	password = "admin";
*/

CREATE DATABASE chatDB;
CREATE TABLE IF NOT EXISTS `chatDB`.`Users` (
  `userID` VARCHAR(40) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`userID`))
ENGINE = InnoDB;

USE `chatDB`;
INSERT INTO `Users` (`userID`,`password`) VALUES 
('user1','123456'),
('user2','123456'),
('user3','123456');


SELECT * FROM `chatDB`.`Users`;