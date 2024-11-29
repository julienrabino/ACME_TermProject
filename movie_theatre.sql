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
    ShowtimeID            INT AUTO_INCREMENT,
    MovieID               INT NOT NULL,
	ShowDate				DATE NOT NULL,
	ShowTime                TIME NOT NULL,
    TheatreID             INT NOT NULL,
    TotalRUSeats          INT NOT NULL,
    TotalOUSeats          INT NOT NULL,
    AvailableRUSeats      INT NOT NULL,
    AvailableOUSeats      INT NOT NULL,

    PRIMARY KEY(ShowtimeID),
    FOREIGN KEY (MovieID) REFERENCES MOVIE(MovieID),
    FOREIGN KEY (TheatreID) REFERENCES THEATRE(TheatreID)
);
INSERT INTO SHOWTIME (MovieID, ShowDate, ShowTime, TheatreID, TotalRUSeats, TotalOUSeats, AvailableRUSeats, AvailableOUSeats) VALUES
(1, '2024-12-01', '10:00:00', 1, 16, 2, 16, 2),
(1, '2024-12-02', '12:30:00', 2, 16, 2, 16, 2),
(1, '2024-12-02', '14:00:00', 3, 16, 2, 16, 2),
(4, '2024-12-03', '16:00:00', 1, 16, 2, 16, 2),
(5, '2024-12-02', '18:30:00', 2, 16, 2, 16, 2),
(6, '2024-12-06', '20:15:00', 3, 16, 2, 16, 2),
(7, '2024-12-02', '11:00:00', 3, 16, 2, 16, 2),
(8, '2024-12-02', '13:45:00', 4, 16, 2, 16, 2),
(9, '2024-12-04', '15:30:00', 4, 16, 2, 16, 2),
(10, '2024-12-02', '17:00:00', 2, 16, 2, 16, 2),
(11, '2024-12-02', '19:00:00', 1, 16, 2, 16, 2),
(11, '2024-12-02', '14:30:00', 2, 16, 2, 16, 2),
(12, '2024-12-02', '21:15:00', 2, 16, 2, 16, 2),
(13, '2024-12-02', '23:00:00', 2, 16, 2, 16, 2),
(14, '2024-12-02', '08:30:00', 3, 16, 2, 16, 2),
(14, '2024-12-03', '10:00:00', 1, 16, 2, 16, 2),
(1, '2024-12-03', '10:45:00', 1, 16, 2, 16, 2),
(2, '2024-12-03', '12:00:00', 3, 16, 2, 16, 2),
(3, '2024-12-03', '14:30:00', 2, 16, 2, 16, 2),
(4, '2024-12-05', '16:45:00', 4, 16, 2, 16, 2),
(5, '2024-12-03', '18:00:00', 4, 16, 2, 16, 2),
(6, '2024-12-04', '19:30:00', 4, 16, 2, 16, 2),
(7, '2024-12-03', '21:00:00', 2, 16, 2, 16, 2),
(8, '2024-12-05', '23:15:00', 2, 16, 2, 16, 2),
(9, '2024-12-06', '09:00:00', 2, 16, 2, 16, 2),
(10, '2024-12-07', '11:30:00', 2, 16, 2, 16, 2),
(11, '2024-12-04', '13:00:00', 1, 16, 2, 16, 2),
(12, '2024-12-04', '15:15:00', 2, 16, 2, 16, 2),
(13, '2024-12-04', '17:45:00', 4, 16, 2, 16, 2),
(14, '2024-12-04', '20:00:00', 3, 16, 2, 16, 2);


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
    SavedPayment		int not null,
	primary key (ID)
);

INSERT INTO REG_USER (Fname, Lname, Email, Username, Password1, Address, MemberShipJoinDate, MemberShipExpiry, SavedPayment)
VALUES
('Jerry', 'Dingle', 'JerryDingle@gmail.com', 'JerryDingle1', 'DingleDongle2', '124 Shrub Street, SE, Calgary', '2024-08-04', '2025-08-04', 0),
('Lucy', 'Lee', 'lucy.lee@yahoo.com', 'peanut', '1loVepeanuts', '312 Maple Dr, Calgary', '2024-05-25', '2025-05-25', 0),
('Alice', 'Johnson', 'alice.johnson@example.com', 'alicejohnson', 'SecurePass123', '456 Maple Ave, Toronto', '2024-10-15', '2025-10-15', 1),
('Bob', 'Smith', 'bob.smith@outlook.com', 'bobby_smith23', 'Bob123456', '789 Oak Road, Vancouver', '2024-09-22', '2025-09-22', 1),
('Emma', 'Williams', 'emma.williams@email.com', 'emma_w', 'EmmaPassword456', '321 Birch Lane, Ottawa', '2024-07-30', '2025-07-30', 0),
('Liam', 'Brown', 'liam.brown@domain.com', 'liam_brown12', 'Liam987654', '101 Pine Street, Montreal', '2024-06-18', '2025-06-18', 1),
('Sophia', 'Davis', 'sophia.davis@aol.com', 'sophiaD', 'Sophia456789', '202 Elm Blvd, Calgary', '2024-11-01', '2025-11-01', 1),
('Jackson', 'Miller', 'jackson.miller@gmail.com', 'jack_miller21', 'Jackson1234', '654 Cedar Drive, Calgary', '2024-08-20', '2025-08-20', 0),
('Olivia', 'Moore', 'olivia.moore@mail.com', 'olivia_moore88', 'OliviaPassword123', '23 Redwood St, Winnipeg', '2024-09-05', '2025-09-05', 1),
('Mason', 'Taylor', 'mason.taylor@outlook.com', 'mason_taylor', 'MasonPass2024', '456 Elm St, Edmonton', '2024-10-10', '2025-10-10', 1),
('Isabella', 'Anderson', 'isabella.anderson@yahoo.com', 'isabella_1', 'IsabellaPassword321', '312 Maple Dr, Calgary', '2024-05-25', '2025-05-25', 0);

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

INSERT INTO PAYMENT (RUID, Fname, Lname, CardNum, ExpiryDate, SecurityCode)
VALUES
(2, 'Alice', 'Johnson', '4111-2345-6789-1234', '12/25', '123'), 
(3, 'Bob', 'Smith', '4222-2345-6789-2345', '11/25', '456'),  
(5, 'Liam', 'Brown', '5105-2345-6789-3456', '01/26', '789'),    
(6, 'Sophia', 'Davis', '4111-2345-6789-4321', '05/26', '012'),   
(9, 'Mason', 'Taylor', '4111-2345-6789-8765', '07/25', '234');   

DROP TABLE IF EXISTS STORE_CREDIT;
CREATE TABLE STORE_CREDIT (
	CreditID			INT AUTO_INCREMENT,
    RUID				int,
    email				varchar(50)	not null,
    ExpiryDate			DATETIME	not null,
	primary key (CreditID),
    foreign key (RUID) references REG_USER(ID)
);

DROP TABLE IF EXISTS SEAT;
CREATE TABLE SEAT (
	SeatID				INT AUTO_INCREMENT,
    Showtime			int not null,
	SeatRow				char(1)	not null,
	SeatColumn			int	not null,
    Available			boolean not null,
    RUReserved			boolean,

	primary key (seatID),
    foreign key (Showtime) references SHOWTIME(ShowtimeID)
);


DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET (
	TicketID			INT AUTO_INCREMENT,
	RUID				int,
    PaymentID			int not null,
    Cost				int not null,
    TimePurchased		TIME not null,
    DatePurchased		DATE not null,
    SeatID              int not null,
	primary key (TicketID),
    foreign key (RUID) references REG_USER(ID),
    foreign key (PaymentID) references PAYMENT(PaymentID),
    foreign key (SeatID) references SEAT(SeatID)
    );


-- Showtime 1
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(1, 'A', 1, TRUE, FALSE),
(1, 'A', 2, TRUE, FALSE),
(1, 'A', 3, TRUE, FALSE),
(1, 'A', 4, TRUE, FALSE),
(1, 'A', 5, TRUE, FALSE),
(1, 'A', 6, TRUE, FALSE),
(1, 'B', 1, TRUE, FALSE),
(1, 'B', 2, TRUE, FALSE),
(1, 'B', 3, TRUE, FALSE),
(1, 'B', 4, TRUE, FALSE),
(1, 'B', 5, TRUE, FALSE),
(1, 'B', 6, TRUE, FALSE),
(1, 'C', 1, TRUE, FALSE),
(1, 'C', 2, TRUE, FALSE),
(1, 'C', 3, TRUE, FALSE),
(1, 'C', 4, TRUE, FALSE),
(1, 'C', 5, TRUE, FALSE),
(1, 'C', 6, TRUE, FALSE);

-- Showtime 2
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(2, 'A', 1, TRUE, FALSE),
(2, 'A', 2, TRUE, FALSE),
(2, 'A', 3, TRUE, FALSE),
(2, 'A', 4, TRUE, FALSE),
(2, 'A', 5, TRUE, FALSE),
(2, 'A', 6, TRUE, FALSE),
(2, 'B', 1, TRUE, FALSE),
(2, 'B', 2, TRUE, FALSE),
(2, 'B', 3, TRUE, FALSE),
(2, 'B', 4, TRUE, FALSE),
(2, 'B', 5, TRUE, FALSE),
(2, 'B', 6, TRUE, FALSE),
(2, 'C', 1, TRUE, FALSE),
(2, 'C', 2, TRUE, FALSE),
(2, 'C', 3, TRUE, FALSE),
(2, 'C', 4, TRUE, FALSE),
(2, 'C', 5, TRUE, FALSE),
(2, 'C', 6, TRUE, FALSE);

-- Showtime 3
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(3, 'A', 1, TRUE, FALSE),
(3, 'A', 2, TRUE, FALSE),
(3, 'A', 3, TRUE, FALSE),
(3, 'A', 4, TRUE, FALSE),
(3, 'A', 5, TRUE, FALSE),
(3, 'A', 6, TRUE, FALSE),
(3, 'B', 1, TRUE, FALSE),
(3, 'B', 2, TRUE, FALSE),
(3, 'B', 3, TRUE, FALSE),
(3, 'B', 4, TRUE, FALSE),
(3, 'B', 5, TRUE, FALSE),
(3, 'B', 6, TRUE, FALSE),
(3, 'C', 1, TRUE, FALSE),
(3, 'C', 2, TRUE, FALSE),
(3, 'C', 3, TRUE, FALSE),
(3, 'C', 4, TRUE, FALSE),
(3, 'C', 5, TRUE, FALSE),
(3, 'C', 6, TRUE, FALSE);

-- Showtime 4
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(4, 'A', 1, TRUE, FALSE),
(4, 'A', 2, TRUE, FALSE),
(4, 'A', 3, TRUE, FALSE),
(4, 'A', 4, TRUE, FALSE),
(4, 'A', 5, TRUE, FALSE),
(4, 'A', 6, TRUE, FALSE),
(4, 'B', 1, TRUE, FALSE),
(4, 'B', 2, TRUE, FALSE),
(4, 'B', 3, TRUE, FALSE),
(4, 'B', 4, TRUE, FALSE),
(4, 'B', 5, TRUE, FALSE),
(4, 'B', 6, TRUE, FALSE),
(4, 'C', 1, TRUE, FALSE),
(4, 'C', 2, TRUE, FALSE),
(4, 'C', 3, TRUE, FALSE),
(4, 'C', 4, TRUE, FALSE),
(4, 'C', 5, TRUE, FALSE),
(4, 'C', 6, TRUE, FALSE);

-- Showtime 5
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(5, 'A', 1, TRUE, FALSE),
(5, 'A', 2, TRUE, FALSE),
(5, 'A', 3, TRUE, FALSE),
(5, 'A', 4, TRUE, FALSE),
(5, 'A', 5, TRUE, FALSE),
(5, 'A', 6, TRUE, FALSE),
(5, 'B', 1, TRUE, FALSE),
(5, 'B', 2, TRUE, FALSE),
(5, 'B', 3, TRUE, FALSE),
(5, 'B', 4, TRUE, FALSE),
(5, 'B', 5, TRUE, FALSE),
(5, 'B', 6, TRUE, FALSE),
(5, 'C', 1, TRUE, FALSE),
(5, 'C', 2, TRUE, FALSE),
(5, 'C', 3, TRUE, FALSE),
(5, 'C', 4, TRUE, FALSE),
(5, 'C', 5, TRUE, FALSE),
(5, 'C', 6, TRUE, FALSE);

-- Showtime 6
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(6, 'A', 1, TRUE, FALSE),
(6, 'A', 2, TRUE, FALSE),
(6, 'A', 3, TRUE, FALSE),
(6, 'A', 4, TRUE, FALSE),
(6, 'A', 5, TRUE, FALSE),
(6, 'A', 6, TRUE, FALSE),
(6, 'B', 1, TRUE, FALSE),
(6, 'B', 2, TRUE, FALSE),
(6, 'B', 3, TRUE, FALSE),
(6, 'B', 4, TRUE, FALSE),
(6, 'B', 5, TRUE, FALSE),
(6, 'B', 6, TRUE, FALSE),
(6, 'C', 1, TRUE, FALSE),
(6, 'C', 2, TRUE, FALSE),
(6, 'C', 3, TRUE, FALSE),
(6, 'C', 4, TRUE, FALSE),
(6, 'C', 5, TRUE, FALSE),
(6, 'C', 6, TRUE, FALSE);

-- Showtime 7
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(7, 'A', 1, TRUE, FALSE),
(7, 'A', 2, TRUE, FALSE),
(7, 'A', 3, TRUE, FALSE),
(7, 'A', 4, TRUE, FALSE),
(7, 'A', 5, TRUE, FALSE),
(7, 'A', 6, TRUE, FALSE),
(7, 'B', 1, TRUE, FALSE),
(7, 'B', 2, TRUE, FALSE),
(7, 'B', 3, TRUE, FALSE),
(7, 'B', 4, TRUE, FALSE),
(7, 'B', 5, TRUE, FALSE),
(7, 'B', 6, TRUE, FALSE),
(7, 'C', 1, TRUE, FALSE),
(7, 'C', 2, TRUE, FALSE),
(7, 'C', 3, TRUE, FALSE),
(7, 'C', 4, TRUE, FALSE),
(7, 'C', 5, TRUE, FALSE),
(7, 'C', 6, TRUE, FALSE);

-- Showtime 8
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(8, 'A', 1, TRUE, FALSE),
(8, 'A', 2, TRUE, FALSE),
(8, 'A', 3, TRUE, FALSE),
(8, 'A', 4, TRUE, FALSE),
(8, 'A', 5, TRUE, FALSE),
(8, 'A', 6, TRUE, FALSE),
(8, 'B', 1, TRUE, FALSE),
(8, 'B', 2, TRUE, FALSE),
(8, 'B', 3, TRUE, FALSE),
(8, 'B', 4, TRUE, FALSE),
(8, 'B', 5, TRUE, FALSE),
(8, 'B', 6, TRUE, FALSE),
(8, 'C', 1, TRUE, FALSE),
(8, 'C', 2, TRUE, FALSE),
(8, 'C', 3, TRUE, FALSE),
(8, 'C', 4, TRUE, FALSE),
(8, 'C', 5, TRUE, FALSE),
(8, 'C', 6, TRUE, FALSE);

-- Showtime 9
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(9, 'A', 1, TRUE, FALSE),
(9, 'A', 2, TRUE, FALSE),
(9, 'A', 3, TRUE, FALSE),
(9, 'A', 4, TRUE, FALSE),
(9, 'A', 5, TRUE, FALSE),
(9, 'A', 6, TRUE, FALSE),
(9, 'B', 1, TRUE, FALSE),
(9, 'B', 2, TRUE, FALSE),
(9, 'B', 3, TRUE, FALSE),
(9, 'B', 4, TRUE, FALSE),
(9, 'B', 5, TRUE, FALSE),
(9, 'B', 6, TRUE, FALSE),
(9, 'C', 1, TRUE, FALSE),
(9, 'C', 2, TRUE, FALSE),
(9, 'C', 3, TRUE, FALSE),
(9, 'C', 4, TRUE, FALSE),
(9, 'C', 5, TRUE, FALSE),
(9, 'C', 6, TRUE, FALSE);
-- Showtime 10
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(10, 'A', 1, TRUE, FALSE),
(10, 'A', 2, TRUE, FALSE),
(10, 'A', 3, TRUE, FALSE),
(10, 'A', 4, TRUE, FALSE),
(10, 'A', 5, TRUE, FALSE),
(10, 'A', 6, TRUE, FALSE),
(10, 'B', 1, TRUE, FALSE),
(10, 'B', 2, TRUE, FALSE),
(10, 'B', 3, TRUE, FALSE),
(10, 'B', 4, TRUE, FALSE),
(10, 'B', 5, TRUE, FALSE),
(10, 'B', 6, TRUE, FALSE),
(10, 'C', 1, TRUE, FALSE),
(10, 'C', 2, TRUE, FALSE),
(10, 'C', 3, TRUE, FALSE),
(10, 'C', 4, TRUE, FALSE),
(10, 'C', 5, TRUE, FALSE),
(10, 'C', 6, TRUE, FALSE);

-- Showtime 11
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(11, 'A', 1, TRUE, FALSE),
(11, 'A', 2, TRUE, FALSE),
(11, 'A', 3, TRUE, FALSE),
(11, 'A', 4, TRUE, FALSE),
(11, 'A', 5, TRUE, FALSE),
(11, 'A', 6, TRUE, FALSE),
(11, 'B', 1, TRUE, FALSE),
(11, 'B', 2, TRUE, FALSE),
(11, 'B', 3, TRUE, FALSE),
(11, 'B', 4, TRUE, FALSE),
(11, 'B', 5, TRUE, FALSE),
(11, 'B', 6, TRUE, FALSE),
(11, 'C', 1, TRUE, FALSE),
(11, 'C', 2, TRUE, FALSE),
(11, 'C', 3, TRUE, FALSE),
(11, 'C', 4, TRUE, FALSE),
(11, 'C', 5, TRUE, FALSE),
(11, 'C', 6, TRUE, FALSE);

-- Showtime 12
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(12, 'A', 1, TRUE, FALSE),
(12, 'A', 2, TRUE, FALSE),
(12, 'A', 3, TRUE, FALSE),
(12, 'A', 4, TRUE, FALSE),
(12, 'A', 5, TRUE, FALSE),
(12, 'A', 6, TRUE, FALSE),
(12, 'B', 1, TRUE, FALSE),
(12, 'B', 2, TRUE, FALSE),
(12, 'B', 3, TRUE, FALSE),
(12, 'B', 4, TRUE, FALSE),
(12, 'B', 5, TRUE, FALSE),
(12, 'B', 6, TRUE, FALSE),
(12, 'C', 1, TRUE, FALSE),
(12, 'C', 2, TRUE, FALSE),
(12, 'C', 3, TRUE, FALSE),
(12, 'C', 4, TRUE, FALSE),
(12, 'C', 5, TRUE, FALSE),
(12, 'C', 6, TRUE, FALSE);
-- Showtime 13
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(13, 'A', 1, TRUE, FALSE),
(13, 'A', 2, TRUE, FALSE),
(13, 'A', 3, TRUE, FALSE),
(13, 'A', 4, TRUE, FALSE),
(13, 'A', 5, TRUE, FALSE),
(13, 'A', 6, TRUE, FALSE),
(13, 'B', 1, TRUE, FALSE),
(13, 'B', 2, TRUE, FALSE),
(13, 'B', 3, TRUE, FALSE),
(13, 'B', 4, TRUE, FALSE),
(13, 'B', 5, TRUE, FALSE),
(13, 'B', 6, TRUE, FALSE),
(13, 'C', 1, TRUE, FALSE),
(13, 'C', 2, TRUE, FALSE),
(13, 'C', 3, TRUE, FALSE),
(13, 'C', 4, TRUE, FALSE),
(13, 'C', 5, TRUE, FALSE),
(13, 'C', 6, TRUE, FALSE);

-- Showtime 14
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(14, 'A', 1, TRUE, FALSE),
(14, 'A', 2, TRUE, FALSE),
(14, 'A', 3, TRUE, FALSE),
(14, 'A', 4, TRUE, FALSE),
(14, 'A', 5, TRUE, FALSE),
(14, 'A', 6, TRUE, FALSE),
(14, 'B', 1, TRUE, FALSE),
(14, 'B', 2, TRUE, FALSE),
(14, 'B', 3, TRUE, FALSE),
(14, 'B', 4, TRUE, FALSE),
(14, 'B', 5, TRUE, FALSE),
(14, 'B', 6, TRUE, FALSE),
(14, 'C', 1, TRUE, FALSE),
(14, 'C', 2, TRUE, FALSE),
(14, 'C', 3, TRUE, FALSE),
(14, 'C', 4, TRUE, FALSE),
(14, 'C', 5, TRUE, FALSE),
(14, 'C', 6, TRUE, FALSE);

-- Showtime 15
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(15, 'A', 1, TRUE, FALSE),
(15, 'A', 2, TRUE, FALSE),
(15, 'A', 3, TRUE, FALSE),
(15, 'A', 4, TRUE, FALSE),
(15, 'A', 5, TRUE, FALSE),
(15, 'A', 6, TRUE, FALSE),
(15, 'B', 1, TRUE, FALSE),
(15, 'B', 2, TRUE, FALSE),
(15, 'B', 3, TRUE, FALSE),
(15, 'B', 4, TRUE, FALSE),
(15, 'B', 5, TRUE, FALSE),
(15, 'B', 6, TRUE, FALSE),
(15, 'C', 1, TRUE, FALSE),
(15, 'C', 2, TRUE, FALSE),
(15, 'C', 3, TRUE, FALSE),
(15, 'C', 4, TRUE, FALSE),
(15, 'C', 5, TRUE, FALSE),
(15, 'C', 6, TRUE, FALSE);

-- Showtime 16
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(16, 'A', 1, TRUE, FALSE),
(16, 'A', 2, TRUE, FALSE),
(16, 'A', 3, TRUE, FALSE),
(16, 'A', 4, TRUE, FALSE),
(16, 'A', 5, TRUE, FALSE),
(16, 'A', 6, TRUE, FALSE),
(16, 'B', 1, TRUE, FALSE),
(16, 'B', 2, TRUE, FALSE),
(16, 'B', 3, TRUE, FALSE),
(16, 'B', 4, TRUE, FALSE),
(16, 'B', 5, TRUE, FALSE),
(16, 'B', 6, TRUE, FALSE),
(16, 'C', 1, TRUE, FALSE),
(16, 'C', 2, TRUE, FALSE),
(16, 'C', 3, TRUE, FALSE),
(16, 'C', 4, TRUE, FALSE),
(16, 'C', 5, TRUE, FALSE),
(16, 'C', 6, TRUE, FALSE);

-- Showtime 17
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(17, 'A', 1, TRUE, FALSE),
(17, 'A', 2, TRUE, FALSE),
(17, 'A', 3, TRUE, FALSE),
(17, 'A', 4, TRUE, FALSE),
(17, 'A', 5, TRUE, FALSE),
(17, 'A', 6, TRUE, FALSE),
(17, 'B', 1, TRUE, FALSE),
(17, 'B', 2, TRUE, FALSE),
(17, 'B', 3, TRUE, FALSE),
(17, 'B', 4, TRUE, FALSE),
(17, 'B', 5, TRUE, FALSE),
(17, 'B', 6, TRUE, FALSE),
(17, 'C', 1, TRUE, FALSE),
(17, 'C', 2, TRUE, FALSE),
(17, 'C', 3, TRUE, FALSE),
(17, 'C', 4, TRUE, FALSE),
(17, 'C', 5, TRUE, FALSE),
(17, 'C', 6, TRUE, FALSE);

-- Showtime 18
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(18, 'A', 1, TRUE, FALSE),
(18, 'A', 2, TRUE, FALSE),
(18, 'A', 3, TRUE, FALSE),
(18, 'A', 4, TRUE, FALSE),
(18, 'A', 5, TRUE, FALSE),
(18, 'A', 6, TRUE, FALSE),
(18, 'B', 1, TRUE, FALSE),
(18, 'B', 2, TRUE, FALSE),
(18, 'B', 3, TRUE, FALSE),
(18, 'B', 4, TRUE, FALSE),
(18, 'B', 5, TRUE, FALSE),
(18, 'B', 6, TRUE, FALSE),
(18, 'C', 1, TRUE, FALSE),
(18, 'C', 2, TRUE, FALSE),
(18, 'C', 3, TRUE, FALSE),
(18, 'C', 4, TRUE, FALSE),
(18, 'C', 5, TRUE, FALSE),
(18, 'C', 6, TRUE, FALSE);

-- Showtime 19
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(19, 'A', 1, TRUE, FALSE),
(19, 'A', 2, TRUE, FALSE),
(19, 'A', 3, TRUE, FALSE),
(19, 'A', 4, TRUE, FALSE),
(19, 'A', 5, TRUE, FALSE),
(19, 'A', 6, TRUE, FALSE),
(19, 'B', 1, TRUE, FALSE),
(19, 'B', 2, TRUE, FALSE),
(19, 'B', 3, TRUE, FALSE),
(19, 'B', 4, TRUE, FALSE),
(19, 'B', 5, TRUE, FALSE),
(19, 'B', 6, TRUE, FALSE),
(19, 'C', 1, TRUE, FALSE),
(19, 'C', 2, TRUE, FALSE),
(19, 'C', 3, TRUE, FALSE),
(19, 'C', 4, TRUE, FALSE),
(19, 'C', 5, TRUE, FALSE),
(19, 'C', 6, TRUE, FALSE);

-- Showtime 20
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(20, 'A', 1, TRUE, FALSE),
(20, 'A', 2, TRUE, FALSE),
(20, 'A', 3, TRUE, FALSE),
(20, 'A', 4, TRUE, FALSE),
(20, 'A', 5, TRUE, FALSE),
(20, 'A', 6, TRUE, FALSE),
(20, 'B', 1, TRUE, FALSE),
(20, 'B', 2, TRUE, FALSE),
(20, 'B', 3, TRUE, FALSE),
(20, 'B', 4, TRUE, FALSE),
(20, 'B', 5, TRUE, FALSE),
(20, 'B', 6, TRUE, FALSE),
(20, 'C', 1, TRUE, FALSE),
(20, 'C', 2, TRUE, FALSE),
(20, 'C', 3, TRUE, FALSE),
(20, 'C', 4, TRUE, FALSE),
(20, 'C', 5, TRUE, FALSE),
(20, 'C', 6, TRUE, FALSE);

-- Showtime 21
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(21, 'A', 1, TRUE, FALSE),
(21, 'A', 2, TRUE, FALSE),
(21, 'A', 3, TRUE, FALSE),
(21, 'A', 4, TRUE, FALSE),
(21, 'A', 5, TRUE, FALSE),
(21, 'A', 6, TRUE, FALSE),
(21, 'B', 1, TRUE, FALSE),
(21, 'B', 2, TRUE, FALSE),
(21, 'B', 3, TRUE, FALSE),
(21, 'B', 4, TRUE, FALSE),
(21, 'B', 5, TRUE, FALSE),
(21, 'B', 6, TRUE, FALSE),
(21, 'C', 1, TRUE, FALSE),
(21, 'C', 2, TRUE, FALSE),
(21, 'C', 3, TRUE, FALSE),
(21, 'C', 4, TRUE, FALSE),
(21, 'C', 5, TRUE, FALSE),
(21, 'C', 6, TRUE, FALSE);

-- Showtime 22
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(22, 'A', 1, TRUE, FALSE),
(22, 'A', 2, TRUE, FALSE),
(22, 'A', 3, TRUE, FALSE),
(22, 'A', 4, TRUE, FALSE),
(22, 'A', 5, TRUE, FALSE),
(22, 'A', 6, TRUE, FALSE),
(22, 'B', 1, TRUE, FALSE),
(22, 'B', 2, TRUE, FALSE),
(22, 'B', 3, TRUE, FALSE),
(22, 'B', 4, TRUE, FALSE),
(22, 'B', 5, TRUE, FALSE),
(22, 'B', 6, TRUE, FALSE),
(22, 'C', 1, TRUE, FALSE),
(22, 'C', 2, TRUE, FALSE),
(22, 'C', 3, TRUE, FALSE),
(22, 'C', 4, TRUE, FALSE),
(22, 'C', 5, TRUE, FALSE),
(22, 'C', 6, TRUE, FALSE);

-- Showtime 23
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(23, 'A', 1, TRUE, FALSE),
(23, 'A', 2, TRUE, FALSE),
(23, 'A', 3, TRUE, FALSE),
(23, 'A', 4, TRUE, FALSE),
(23, 'A', 5, TRUE, FALSE),
(23, 'A', 6, TRUE, FALSE),
(23, 'B', 1, TRUE, FALSE),
(23, 'B', 2, TRUE, FALSE),
(23, 'B', 3, TRUE, FALSE),
(23, 'B', 4, TRUE, FALSE),
(23, 'B', 5, TRUE, FALSE),
(23, 'B', 6, TRUE, FALSE),
(23, 'C', 1, TRUE, FALSE),
(23, 'C', 2, TRUE, FALSE),
(23, 'C', 3, TRUE, FALSE),
(23, 'C', 4, TRUE, FALSE),
(23, 'C', 5, TRUE, FALSE),
(23, 'C', 6, TRUE, FALSE);

-- Showtime 24
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(24, 'A', 1, TRUE, FALSE),
(24, 'A', 2, TRUE, FALSE),
(24, 'A', 3, TRUE, FALSE),
(24, 'A', 4, TRUE, FALSE),
(24, 'A', 5, TRUE, FALSE),
(24, 'A', 6, TRUE, FALSE),
(24, 'B', 1, TRUE, FALSE),
(24, 'B', 2, TRUE, FALSE),
(24, 'B', 3, TRUE, FALSE),
(24, 'B', 4, TRUE, FALSE),
(24, 'B', 5, TRUE, FALSE),
(24, 'B', 6, TRUE, FALSE),
(24, 'C', 1, TRUE, FALSE),
(24, 'C', 2, TRUE, FALSE),
(24, 'C', 3, TRUE, FALSE),
(24, 'C', 4, TRUE, FALSE),
(24, 'C', 5, TRUE, FALSE),
(24, 'C', 6, TRUE, FALSE);

-- Showtime 25
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(25, 'A', 1, TRUE, FALSE),
(25, 'A', 2, TRUE, FALSE),
(25, 'A', 3, TRUE, FALSE),
(25, 'A', 4, TRUE, FALSE),
(25, 'A', 5, TRUE, FALSE),
(25, 'A', 6, TRUE, FALSE),
(25, 'B', 1, TRUE, FALSE),
(25, 'B', 2, TRUE, FALSE),
(25, 'B', 3, TRUE, FALSE),
(25, 'B', 4, TRUE, FALSE),
(25, 'B', 5, TRUE, FALSE),
(25, 'B', 6, TRUE, FALSE),
(25, 'C', 1, TRUE, FALSE),
(25, 'C', 2, TRUE, FALSE),
(25, 'C', 3, TRUE, FALSE),
(25, 'C', 4, TRUE, FALSE),
(25, 'C', 5, TRUE, FALSE),
(25, 'C', 6, TRUE, FALSE);

-- Showtime 26
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(26, 'A', 1, TRUE, FALSE),
(26, 'A', 2, TRUE, FALSE),
(26, 'A', 3, TRUE, FALSE),
(26, 'A', 4, TRUE, FALSE),
(26, 'A', 5, TRUE, FALSE),
(26, 'A', 6, TRUE, FALSE),
(26, 'B', 1, TRUE, FALSE),
(26, 'B', 2, TRUE, FALSE),
(26, 'B', 3, TRUE, FALSE),
(26, 'B', 4, TRUE, FALSE),
(26, 'B', 5, TRUE, FALSE),
(26, 'B', 6, TRUE, FALSE),
(26, 'C', 1, TRUE, FALSE),
(26, 'C', 2, TRUE, FALSE),
(26, 'C', 3, TRUE, FALSE),
(26, 'C', 4, TRUE, FALSE),
(26, 'C', 5, TRUE, FALSE),
(26, 'C', 6, TRUE, FALSE);

-- Showtime 27
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(27, 'A', 1, TRUE, FALSE),
(27, 'A', 2, TRUE, FALSE),
(27, 'A', 3, TRUE, FALSE),
(27, 'A', 4, TRUE, FALSE),
(27, 'A', 5, TRUE, FALSE),
(27, 'A', 6, TRUE, FALSE),
(27, 'B', 1, TRUE, FALSE),
(27, 'B', 2, TRUE, FALSE),
(27, 'B', 3, TRUE, FALSE),
(27, 'B', 4, TRUE, FALSE),
(27, 'B', 5, TRUE, FALSE),
(27, 'B', 6, TRUE, FALSE),
(27, 'C', 1, TRUE, FALSE),
(27, 'C', 2, TRUE, FALSE),
(27, 'C', 3, TRUE, FALSE),
(27, 'C', 4, TRUE, FALSE),
(27, 'C', 5, TRUE, FALSE),
(27, 'C', 6, TRUE, FALSE);

-- Showtime 28
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(28, 'A', 1, TRUE, FALSE),
(28, 'A', 2, TRUE, FALSE),
(28, 'A', 3, TRUE, FALSE),
(28, 'A', 4, TRUE, FALSE),
(28, 'A', 5, TRUE, FALSE),
(28, 'A', 6, TRUE, FALSE),
(28, 'B', 1, TRUE, FALSE),
(28, 'B', 2, TRUE, FALSE),
(28, 'B', 3, TRUE, FALSE),
(28, 'B', 4, TRUE, FALSE),
(28, 'B', 5, TRUE, FALSE),
(28, 'B', 6, TRUE, FALSE),
(28, 'C', 1, TRUE, FALSE),
(28, 'C', 2, TRUE, FALSE),
(28, 'C', 3, TRUE, FALSE),
(28, 'C', 4, TRUE, FALSE),
(28, 'C', 5, TRUE, FALSE),
(28, 'C', 6, TRUE, FALSE);

-- Showtime 29
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(29, 'A', 1, TRUE, FALSE),
(29, 'A', 2, TRUE, FALSE),
(29, 'A', 3, TRUE, FALSE),
(29, 'A', 4, TRUE, FALSE),
(29, 'A', 5, TRUE, FALSE),
(29, 'A', 6, TRUE, FALSE),
(29, 'B', 1, TRUE, FALSE),
(29, 'B', 2, TRUE, FALSE),
(29, 'B', 3, TRUE, FALSE),
(29, 'B', 4, TRUE, FALSE),
(29, 'B', 5, TRUE, FALSE),
(29, 'B', 6, TRUE, FALSE),
(29, 'C', 1, TRUE, FALSE),
(29, 'C', 2, TRUE, FALSE),
(29, 'C', 3, TRUE, FALSE),
(29, 'C', 4, TRUE, FALSE),
(29, 'C', 5, TRUE, FALSE),
(29, 'C', 6, TRUE, FALSE);

-- Showtime 30
INSERT INTO SEAT (Showtime, SeatRow, SeatColumn, Available, RUReserved)
VALUES
(30, 'A', 1, TRUE, FALSE),
(30, 'A', 2, TRUE, FALSE),
(30, 'A', 3, TRUE, FALSE),
(30, 'A', 4, TRUE, FALSE),
(30, 'A', 5, TRUE, FALSE),
(30, 'A', 6, TRUE, FALSE),
(30, 'B', 1, TRUE, FALSE),
(30, 'B', 2, TRUE, FALSE),
(30, 'B', 3, TRUE, FALSE),
(30, 'B', 4, TRUE, FALSE),
(30, 'B', 5, TRUE, FALSE),
(30, 'B', 6, TRUE, FALSE),
(30, 'C', 1, TRUE, FALSE),
(30, 'C', 2, TRUE, FALSE),
(30, 'C', 3, TRUE, FALSE),
(30, 'C', 4, TRUE, FALSE),
(30, 'C', 5, TRUE, FALSE),
(30, 'C', 6, TRUE, FALSE);







