
--Use with MySQL
USE test;
SET GLOBAL time_zone = '-6:00';
DROP TABLE IF EXISTS part;
CREATE TABLE part(
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    quantity INT,
    necessity BOOLEAN,
    PRIMARY KEY (id)
)
ENGINE = InnoDB
DEFAULT  CHARACTER SET = utf8;

INSERT INTO part (id, name, necessity, quantity) VALUES (1, 'Motherboard', 1, '3');
INSERT INTO part (id, name, necessity, quantity) VALUES (2, 'Sound card', 0, '10');
INSERT INTO part (id, name, necessity, quantity) VALUES (3, 'CPU', 1, '2');
INSERT INTO part (id, name, necessity, quantity) VALUES (4, 'Frame lighting', 0, '12');
INSERT INTO part (id, name, necessity, quantity) VALUES (5, 'HDD', 0, '21');
INSERT INTO part (id, name, necessity, quantity) VALUES (6, 'Frame', 1, '5');
INSERT INTO part (id, name, necessity, quantity) VALUES (7, 'Memory', 1, '31');
INSERT INTO part (id, name, necessity, quantity) VALUES (8, 'SDD', 1, '15');
INSERT INTO part (id, name, necessity, quantity) VALUES (9, 'GPU', 0, '5');

--Use with H2
--INSERT INTO part (id, name, necessity, quantity) VALUES (1, 'Motherboard', 1, '3');
--INSERT INTO part (id, name, necessity, quantity) VALUES (2, 'Sound card', 0, '10');
--INSERT INTO part (id, name, necessity, quantity) VALUES (3, 'CPU', 1, '2');
--INSERT INTO part (id, name, necessity, quantity) VALUES (4, 'Frame lighting', 0, '12');
--INSERT INTO part (id, name, necessity, quantity) VALUES (5, 'HDD', 0, '21');
--INSERT INTO part (id, name, necessity, quantity) VALUES (6, 'Frame', 1, '5');
--INSERT INTO part (id, name, necessity, quantity) VALUES (7, 'Memory', 1, '31');
--INSERT INTO part (id, name, necessity, quantity) VALUES (8, 'SDD', 1, '15');
--INSERT INTO part (id, name, necessity, quantity) VALUES (9, 'GPU', 0, '5');