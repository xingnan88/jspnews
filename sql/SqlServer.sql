-- ********************************************************************
-- 梦想年华新闻系统 [DreamTimeNews] 
-- SqlServer数据库角本文件

-- 数据库: `dreamtimenews`
-- 



/****** Object:  Database DreamTimeNews    Script Date: 2006-3-22 21:15:44 ******/
CREATE DATABASE [DreamTimeNews]  ON (NAME = N'DreamTimeNews_Data', FILENAME = N'C:\Microsoft SQL Server\MSSQL\data\DreamTimeNews_Data.MDF' , SIZE = 2, FILEGROWTH = 10%) LOG ON (NAME = N'DreamTimeNews_Log', FILENAME = N'C:\Microsoft SQL Server\MSSQL\data\DreamTimeNews_Log.LDF' , SIZE = 1, FILEGROWTH = 10%)
 COLLATE Chinese_PRC_CI_AS
GO

if( (@@microsoftversion / power(2, 24) = 8) and (@@microsoftversion & 0xffff >= 724) )
	exec sp_dboption N'DreamTimeNews', N'db chaining', N'false'
GO

use [DreamTimeNews]
GO

/****** Object:  Login dreamnews    Script Date: 2006-3-22 21:15:44 ******/
if not exists (select * from master.dbo.syslogins where loginname = N'dreamnews')
BEGIN
	declare @logindb nvarchar(132), @loginlang nvarchar(132) select @logindb = N'DreamTimeNews', @loginlang = N'简体中文'
	if @logindb is null or not exists (select * from master.dbo.sysdatabases where name = @logindb)
		select @logindb = N'master'
	if @loginlang is null or (not exists (select * from master.dbo.syslanguages where name = @loginlang) and @loginlang <> N'us_english')
		select @loginlang = @@language
	exec sp_addlogin N'dreamnews', null, @logindb, @loginlang
END
GO

/****** Object:  Login dreamnews    Script Date: 2006-3-22 21:15:45 ******/
exec sp_addsrvrolemember N'dreamnews', dbcreator
GO

/****** Object:  User dreamnews    Script Date: 2006-3-22 21:15:45 ******/
if not exists (select * from dbo.sysusers where name = N'dreamnews' and uid < 16382)
	EXEC sp_grantdbaccess N'dreamnews', N'dreamnews'
GO

/****** Object:  User dreamnews    Script Date: 2006-3-22 21:15:45 ******/
exec sp_addrolemember N'db_accessadmin', N'dreamnews'
GO

/****** Object:  User dreamnews    Script Date: 2006-3-22 21:15:45 ******/
exec sp_addrolemember N'db_owner', N'dreamnews'
GO

/****** Object:  Table [dbo].[Admin]    Script Date: 2006-3-22 21:15:45 ******/
CREATE TABLE [dbo].[Admin] (
	[AdminID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[AdminName] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[AdminPwd] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NULL ,
	[AdminType] [smallint] NULL ,
	[AddTime] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[LastLoginTime] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[LastLoginIP] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsNum] [int] NULL ,
	[LoginNum] [int] NULL ,
	[UserName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserSex] [nvarchar] (2) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserBirthday] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserEmail] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserQQ] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserTel] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserAddress] [nvarchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserZip] [nvarchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[UserInfo] [ntext] COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[BigClass]    Script Date: 2006-3-22 21:15:47 ******/
CREATE TABLE [dbo].[BigClass] (
	[BigClassID] [int] NOT NULL ,
	[BigClassName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[BigClassInfo] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,
	[AddTime] [smalldatetime] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Config]    Script Date: 2006-3-22 21:15:48 ******/
CREATE TABLE [dbo].[Config] (
	[ConfigID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[AdminUserListNum] [smallint] NULL ,
	[AdminLogListNum] [smallint] NULL ,
	[AdminNewsListNum] [smallint] NULL ,
	[HotNewsNum] [smallint] NULL ,
	[HeadNewsNum] [smallint] NULL ,
	[TopNewsNum] [smallint] NULL ,
	[TopImgNum] [smallint] NULL ,
	[ClassNewsNum] [smallint] NULL ,
	[ClassImgNum] [smallint] NULL ,
	[SpecNum] [smallint] NULL ,
	[SpecNewsNum] [smallint] NULL ,
	[BHotNewsNum] [smallint] NULL ,
	[BHeadNewsNum] [smallint] NULL ,
	[BTopNewsNum] [smallint] NULL ,
	[BTopImgNum] [smallint] NULL ,
	[BClassNewsNum] [smallint] NULL ,
	[BClassImgNum] [smallint] NULL ,
	[BSpecNum] [smallint] NULL ,
	[BSpecNewsNum] [smallint] NULL ,
	[ListSpecNum] [smallint] NULL ,
	[ListNewsNum] [smallint] NULL ,
	[SearchNewsNum] [smallint] NULL ,
	[DreamNewsTitle] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[DreamNewsCopyRight] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[DreamNewsEmail] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Log]    Script Date: 2006-3-22 21:15:49 ******/
CREATE TABLE [dbo].[Log] (
	[LogID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[User] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL ,
	[LogType] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[LogTime] [smalldatetime] NULL ,
	[IP] [nvarchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[Result] [nvarchar] (3) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[News]    Script Date: 2006-3-22 21:15:49 ******/
CREATE TABLE [dbo].[News] (
	[NewsID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[NewsTitle] [nvarchar] (60) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsContent] [ntext] COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsKey] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsAuthor] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsFrom] [nvarchar] (60) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsTime] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[NewsPicture] [nvarchar] (120) COLLATE Chinese_PRC_CI_AS NULL ,
	[BigClassID] [int] NULL ,
	[SmallClassID] [int] NULL ,
	[IsHead] [nvarchar] (3) COLLATE Chinese_PRC_CI_AS NULL ,
	[HeadPicture] [nvarchar] (120) COLLATE Chinese_PRC_CI_AS NULL ,
	[IsImg] [nvarchar] (3) COLLATE Chinese_PRC_CI_AS NULL ,
	[IsHot] [nvarchar] (3) COLLATE Chinese_PRC_CI_AS NULL ,
	[SpecialID] [int] NULL ,
	[NewsInfo] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,
	[AdminName] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[SmallClass]    Script Date: 2006-3-22 21:15:50 ******/
CREATE TABLE [dbo].[SmallClass] (
	[SmallClassID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[SmallClassName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[SmallClassInfo] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[BigClassID] [smallint] NULL ,
	[AddTime] [smalldatetime] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Special]    Script Date: 2006-3-22 21:15:50 ******/
CREATE TABLE [dbo].[Special] (
	[SpecialID] [numeric](10, 0) IDENTITY (1, 1) NOT NULL ,
	[SpecialName] [nvarchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[SpecialInfo] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL ,
	[SpecialTime] [smalldatetime] NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Admin] ADD 
	CONSTRAINT [PK_Admin] PRIMARY KEY  CLUSTERED 
	(
		[AdminID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[BigClass] ADD 
	CONSTRAINT [PK_BigClass] PRIMARY KEY  CLUSTERED 
	(
		[BigClassID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Config] ADD 
	CONSTRAINT [PK_Config] PRIMARY KEY  CLUSTERED 
	(
		[ConfigID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Log] ADD 
	CONSTRAINT [PK_Log] PRIMARY KEY  CLUSTERED 
	(
		[LogID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[News] ADD 
	CONSTRAINT [PK_News] PRIMARY KEY  CLUSTERED 
	(
		[NewsID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[SmallClass] ADD 
	CONSTRAINT [PK_SmallClass] PRIMARY KEY  CLUSTERED 
	(
		[SmallClassID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Special] ADD 
	CONSTRAINT [PK_Special] PRIMARY KEY  CLUSTERED 
	(
		[SpecialID]
	)  ON [PRIMARY] 
GO


insert into admin (AdminName,AdminPwd,AdminType,UserName,UserSex,UserBirthday,UserEmail)
values ('admin','CB939D9EA1198112BF8EDCD343ED2A1D',3,'梦想年华','男','1900-1-1','fanwsp@126.com')
go

insert into Config values (10,50,50,8,2,10,6,10,6,10,10,4,1,10,6,10,6,8,8,10,50,50,'梦想年华新闻系统','梦想年华[DreamTime','fanwsp@126.com')




