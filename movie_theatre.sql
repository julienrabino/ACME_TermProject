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
('Location B','AcmePlex: Location B'),
('Westhills','AcmePlex: Westills bloop'),
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
	MovieID					INT NOT NULL,
	Showtime				DATETIME NOT NULL,
    TheatreID				INT NOT NULL,

    PRIMARY KEY(ShowtimeID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID),
    FOREIGN KEY (TheatreID) REFERENCES THEATRE(TheatreID)
);

INSERT INTO SHOWTIME (MovieID, Showtime, TheatreID) VALUES
(1, '2024-12-02 10:00:00', 2),
(2, '2024-12-02 12:30:00', 1),
(3, '2024-12-02 14:00:00', 1),
(4, '2024-12-02 16:00:00', 1),
(5, '2024-12-02 18:30:00', 1),
(6, '2024-12-02 20:15:00', 1),
(7, '2024-12-02 11:00:00', 1),
(8, '2024-12-02 13:45:00', 1),
(9, '2024-12-02 15:30:00', 1),
(10, '2024-12-02 17:00:00', 1),
(11, '2024-12-02 19:00:00', 2),
(11, '2024-12-02 14:30:00', 2),
(12, '2024-12-02 21:15:00', 2),
(13, '2024-12-02 23:00:00', 2),
(14, '2024-12-02 08:30:00', 2),
(14, '2024-12-02 010:00:00', 2),
(1, '2024-12-03 10:45:00', 3),
(2, '2024-12-03 12:00:00', 3),
(3, '2024-12-03 14:30:00', 3),
(4, '2024-12-03 16:45:00', 3),
(5, '2024-12-03 18:00:00', 3),
(6, '2024-12-03 19:30:00', 3),
(7, '2024-12-03 21:00:00', 3),
(8, '2024-12-04 23:15:00', 4),
(9, '2024-12-04 09:00:00', 4),
(10, '2024-12-04 11:30:00', 4),
(11, '2024-12-04 13:00:00', 4),
(12, '2024-12-04 15:15:00', 4),
(13, '2024-12-04 17:45:00', 4),
(14, '2024-12-04 20:00:00', 4);


DROP TABLE IF EXISTS REG_USER;
CREATE TABLE REG_USER (
	ID					INT AUTO_INCREMENT,
	Fname				varchar(50)	not null,
	Lname				varchar(50)	not null,
    Email				varchar(50)	not null,
    Username			varchar(20)	not null,
    Password1			varchar(20) not null,
    Address				varchar(50)	not null,
    MemberShipJoinDate	DATE NOT NULL,
    MemberShipExpiry	DATE NOT NULL,
    PaymentMethod		varchar(50),
	primary key (ID)
);

DROP TABLE IF EXISTS PAYMENT;
CREATE TABLE PAYMENT (
	PaymentID			INT AUTO_INCREMENT,
    RUID				int,
	Fname				varchar(50)	not null,
	Lname				varchar(50)	not null,
    CardNum				varchar(50)	not null,
    ExpiryDate			varchar(20)	not null,
    SecurityCode		varchar(20) not null,
	primary key (PaymentID),
    foreign key (RUID) references REG_USER(ID)
);

DROP TABLE IF EXISTS STORE_CREDIT;
CREATE TABLE STORE_CREDIT (
	CreditID			INT AUTO_INCREMENT,
    RUID				int,
    email				varchar(50)	not null,
    ExpiryDate			DATETIME	not null,
	primary key (CreditID),
    foreign key (RUID) references REG_USER(ID)
);


DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
	TicketID			INT AUTO_INCREMENT,
	RUID				int,
    PaymentID			int not null,
    Cost				int not null,
    TimePurchased		DATETIME not null,
	primary key (TicketID),
    foreign key (RUID) references REG_USER(ID),
    foreign key (PaymentID) references PAYMENT(PaymentID)
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






