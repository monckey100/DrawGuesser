IF EXISTS (SELECT name FROM sys.databases WHERE name = N'DrawGuesser')
    DROP DATABASE [DrawGuesser]
GO

CREATE DATABASE DrawGuesser;
GO

 

use DrawGuesser;

use jspweb;

create table  _User
	(UserID				int Not Null primary key  Identity(1,1), 
	 Fname				varchar(64) Not Null,
	 Lname				varchar(64) Not Null,
	 userName			varchar(64) not null unique,
	 _Password			varchar(64) Not Null,
	 Email				varchar(64) Not Null ,
	 _Level				int Not Null,
	 _Exp				int Not Null
	);

create table Word_Category
	(CategoryID				int Not Null primary key Identity(1,1), 
	 CatagoryName			varchar(64) Not Null
);
create table Words(
	 WordID					int Not Null primary key Identity(1,1), 
	 CategoryID			int Not Null,
	 WordName				varchar(64) Not Null
	 foreign key(CategoryID) references Word_Category(CategoryID)
	 
)
create table DifficultyLevel
	(DifficultyLevel		nvarchar(25) Not Null primary key, 
	 TimePeriod				int Not Null
	);


create table Drawing
	(DrawingID				int Not Null primary key Identity(1,1), 
	 WordID					int Not Null, 
	 DifficultyLevel		nvarchar(25) Not Null,
	 UserID					int Not Null,
	 DrawingData            varBinary(MAX),

	  foreign key (UserID) references _User(UserID),
	  foreign key (WordID) references Words(WordID) ,
	  foreign key (DifficultyLevel) references DifficultyLevel(DifficultyLevel) 

	);


Create table Guess
	(GuessingID				int Not Null primary key Identity(1,1),
	 DifficultyLevel		nvarchar(25) Not Null,
	 DrawingID				int Not Null,
	 UserID					int Not Null,
	 SucceedTimes			int Not Null,
	 TotalTime				int Not Null,

	  foreign key (DifficultyLevel) references DifficultyLevel(DifficultyLevel),
	  foreign key (DrawingID) references Drawing(DrawingID) ,
	  foreign key (UserID) references _User(UserID)
	);


insert into Word_Category (CatagoryName) values 
	('Fruit'),
	('Animal'),
	('Games');

insert into  Words (CategoryID, WordName) values 
	( 1, 'Apple'),
	( 1, 'Papaya'),
	( 1, 'Banana'),
	( 2, 'Cat'),
	( 2, 'Dog'),
	( 2, 'Fish'),
	( 3, 'Mario'),
	( 3, 'Luigi'),
	( 3, 'Sanic');
insert into  DifficultyLevel values 
	('Easy', 180),
	('Intermediate', 120),
	('Hard', 60);
Select WordID,Word_Category.CatagoryName,WordName From Words
	JOIN Word_Category ON Word_Category.CategoryID = Words.CategoryID;

Select * from _User 

delete from _User
DBCC CHECKIDENT ('_User', RESEED, 0)
GO

Select	*
From _User 


Select * 
from _User
Where userName ='A' ;

insert into _User 
values('bbbb','bbb','bbb','bbb','bbb',1,1);


Drop table _User, Word_Category,Words,Drawing,Guess,DifficultyLevel


USE [jspweb]
GO
/****** Object:  Table [dbo].[_User]    Script Date: 2019-03-19 9:35:09 PM ******/
DROP TABLE [dbo].[_User]
GO


ALTER TABLE [dbo].[_User] DROP CONSTRAINT [PK___User__1788CCAC10C937E4]