DROP DATABASE IF EXISTS MOVIE_THEATRE;
CREATE DATABASE MOVIE_THEATRE;
USE MOVIE_THEATRE;

DROP TABLE IF EXISTS THEATRE;
CREATE TABLE THEATRE (
	TheatreID				INT AUTO_INCREMENT,
	Address					varchar(100) not null,
	TheatreName				varchar(100) not null,
    primary key(TheatreID)
);
INSERT INTO THEATRE (Address, TheatreName)
VALUES
('Location A','AcmePlex: Location A'),
('Location B','AcmePlex: Location A'),
('Westhills','AcmePlex: Westills'),
('Candy Cane Lane, North Pole','AcmePlex: Santa Edition');

DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
	MovieID					INT AUTO_INCREMENT,
	Title					varchar(100)	not null,
	Genre					varchar(100)	not null,
    primary key(MovieID)
);

INSERT INTO MOVIE (Title, Genre)
VALUES
('The Garfeild Movie','Fat Cat, Animated, Lasagna'),
('The Wild Robot','Adventure, Family, Animated'),
('The Barbie Movie','Societal Issues'),
('Barbie as the Three Musketeers','Action, Friendship, Animated'),
('Barbie as the Island Princess','Tropical, Adventure, Animated'),
('Barbie in the Princess Charm School','Magical, Redemption Arc, Animated'),
('The Spongebob Movie','Comedy, Adventure, Animated'),
('Clueless','Rom-Com'),
('Diary of a Wimpy Kid: Rodrick Rules','Comedy, Slice of Life?'),
('Kimi No Nawa','Anime, Romance'),
('Home Alone','Christmas, Comedy'),
('Home Alone 2','Christmas, Comedy'),
('Home Alone 3','Christmas, Comedy'),
('The Grinch','Christmas, Kindness, Animated');

DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
	ShowtimeID				INT AUTO_INCREMENT,
	MovieID					int	not null,
	Time					TIME NOT NULL,
    TheatreID				int not null,

    primary key(ShowtimeID),
    foreign key (MovieID) references MOVIE(MovieID),
    foreign key (TheatreID) references THEATRE(TheatreID)
);

INSERT INTO SHOWTIME (MovieID, Time, TheatreID) VALUES
(1, '10:00:00', 1),
(2, '12:30:00', 1),
(3, '14:00:00', 1),
(4, '16:00:00', 1),
(5, '18:30:00', 1),
(6, '20:15:00', 1),
(7, '11:00:00', 1),
(8, '13:45:00', 1),
(9, '15:30:00', 1),
(10, '17:00:00', 1),
(11, '19:00:00', 2),
(11, '14:30:00', 2),
(12, '21:15:00', 2),
(13, '23:00:00', 2),
(14, '08:30:00', 2),
(1, '10:45:00', 3),
(2, '12:00:00', 3),
(3, '14:30:00', 3),
(4, '16:45:00', 3),
(5, '18:00:00', 3),
(6, '19:30:00', 3),
(7, '21:00:00', 3),
(8, '23:15:00', 4),
(9, '09:00:00', 4),
(10, '11:30:00', 4),
(11, '13:00:00', 4),
(12, '15:15:00', 4),
(13, '17:45:00', 4),
(14, '20:00:00', 4);




DROP TABLE IF EXISTS REG_USER;
CREATE TABLE REG_USER (
	ID					INT AUTO_INCREMENT,
	Fname				varchar(50)	not null,
	Lname				varchar(50)	not null,
    Email				varchar(50)	not null,
    Address				varchar(50)	not null,
    MemberShipJoinDate	DATE NOT NULL,
    MemberShipExpiry	DATE NOT NULL,
    PaymentMethod		varchar(50),
	primary key (ID)
);

DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
	SeatID				INT AUTO_INCREMENT,
    Showtime			int not null,
    Theatre				int not null,
    Movie				int not null,
	SeatRow				char(1)	not null,
	SeatColumn			int	not null,
    Available			int not null,
    RegUser				int,

	primary key (seatID),
    foreign key (RegUser) references REG_USER(ID),
    foreign key (Theatre) references THEATRE(TheatreID),
    foreign key (Movie) references MOVIE(MovieID),
    foreign key (Showtime) references SHOWTIME(ShowtimeID)
);




