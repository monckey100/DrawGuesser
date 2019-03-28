IF EXISTS (SELECT name FROM sys.databases WHERE name = N'DrawGuesser')
    DROP DATABASE [DrawGuesser]
GO

CREATE DATABASE DrawGuesser;
GO

 

use DrawGuesser;



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
	 DrawingData            nvarchar(MAX),

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

-- Ensure that user with correct answer for specific image will never receive that image again
Create table Correct_Guess
	(
	UserID int not null ,
	DrawingID	int not null
	constraint PK_UserID_DrawingID primary key (UserID,DrawingID),
	constraint FK_UserID foreign key (UserID) references _User(UserID),
	constraint FK_DrawingID foreign key (DrawingID) references Drawing(DrawingID)

	)

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
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      /9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAG7AkEDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDyPwV4K1Lx5rM2l6XPaQzxW7XDNdOyqVDKuBtVjnLjt613n/DOPjD/AKCWh/8Af+b/AONUfs4/8lD1D/sFSf8Ao2Kvp+gD5g/4Zx8Yf9BLQ/8Av/N/8ao/4Zx8Yf8AQS0P/v8Azf8Axqvp+igD5Yf9n7xPHqMNg2raGLqaKSaN
Select * from _User 
Select * from Guess
select   * from drawing
select * from Correct_Guess
insert into drawing(UserID,WordID,DifficultyLevel,DrawingData) select 1,1,'Easy', convert(varbinary(max),'asd')

delete from drawing
DBCC CHECKIDENT ('drawing', RESEED,0)
GO

   SELECT sum(SucceedTimes) as ss , sum(TotalTime) as total
from Guess 
where DrawingID =2



SELECT top 1 WordName, WordID
					 FROM Words join Word_Category on Words.CategoryID = Word_Category.CategoryID
				--	 WHERE CatagoryName = 'Fruit'
					 ORDER BY NEWID()

Select	*

From Words

ORDER BY NEWID()
 
 select (99/100),userName from _User



