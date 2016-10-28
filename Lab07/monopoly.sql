--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PlayerGame;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Property;

-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY, 
	time timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY, 
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);
	
CREATE TABLE Property (
	ID integer PRIMARY KEY,
	cash integer,
	numhouses integer,
	numhotels integer,
	position integer
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID), 
	playerID integer REFERENCES Player(ID),
	score integer,
	property Property(ID)
	);

-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON Property TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO Property (1, 1250, 3, 2, 20)
INSERT INTO Property (2, 2000, 2, 2, 42)
INSERT INTO Property (3, 375, 1, 1, 6)
INSERT INTO Property (4, 345, 2, 0, 20)
INSERT INTO Property (5, 780, 2, 1, 11)
INSERT INTO Property (6, 1200, 0, 3, 41)
INSERT INTO Property (7, 1020, 6, 2, 4)
INSERT INTO Property (8, 570, 8, 2, 9)

INSERT INTO PlayerGame VALUES (1, 1, 0.00, 1);
INSERT INTO PlayerGame VALUES (1, 2, 0.00, 2);
INSERT INTO PlayerGame VALUES (1, 3, 2350.00, 3);
INSERT INTO PlayerGame VALUES (2, 1, 1000.00, 4);
INSERT INTO PlayerGame VALUES (2, 2, 0.00, 5);
INSERT INTO PlayerGame VALUES (2, 3, 500.00, 6);
INSERT INTO PlayerGame VALUES (3, 2, 0.00, 7);
INSERT INTO PlayerGame VALUES (3, 3, 5500.00, 8);
