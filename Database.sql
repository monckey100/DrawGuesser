use DrawGuesser;

create table  _User
	(UserID				int Not Null primary key  Identity(1,1), 
	 Fname				varchar(64) Not Null,
	 Lname				varchar(64) Not Null,
	 _Password			varchar(64) Not Null,
	 Email				varchar(64) Not Null,
	 _Level				int Not Null,
	 _Exp				int Not Null
	);

create table Word_Catagory
	(WordID					int Not Null primary key Identity(1,1), 
	 WordName				varchar(64) Not Null, 
	 CatagoryName			varchar(64) Not Null
	);

create table DifficultyLevel
	(DifficultyLevel		int Not Null primary key, 
	 TimePeriod				int Not Null
	);


create table Drawing
	(DrawingID				int Not Null primary key Identity(1,1), 
	 WordID					int Not Null, 
	 DifficultyLevel		int Not Null,
	 UserID					int Not Null,
	 DrawingData            varBinary(MAX),

	  foreign key (UserID) references _User(UserID),
	  foreign key (WordID) references Word_Catagory(WordID) ,
	  foreign key (DifficultyLevel) references DifficultyLevel(DifficultyLevel) 

	);


Create table Guess
	(GuessingID				int Not Null primary key Identity(1,1),
	 DifficultyLevel		int Not Null,
	 DrawingID				int Not Null,
	 UserID					int Not Null,
	 SucceedTimes			int Not Null,
	 TotalTime				int Not Null,

	  foreign key (DifficultyLevel) references DifficultyLevel(DifficultyLevel),
	  foreign key (DrawingID) references Drawing(DrawingID) ,
	  foreign key (UserID) references _User(UserID)
	);




insert into  Word_Catagory values ( 'Apple', 'Fruit');
insert into  Word_Catagory values ( 'papaya', 'Fruit');
insert into  Word_Catagory values ( 'banana', 'Fruit');

insert into  Word_Catagory values ( 'Cat', 'Animal');
insert into  Word_Catagory values ( 'Dog', 'Animal');
insert into  Word_Catagory values ( 'Fish', 'Animal');

insert into  DifficultyLevel values (1, 180);
insert into  DifficultyLevel values (2, 120);
insert into  DifficultyLevel values (3, 60);

Select *
From Word_Catagory