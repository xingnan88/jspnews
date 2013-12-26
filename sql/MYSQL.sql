-- ********************************************************************
-- 梦想年华新闻系统 [DreamTimeNews] 
-- MySQL数据库角本文件

-- 数据库: `dreamtimenews`
-- 
-- 
-- 数据库: `dreamtimenews`
-- 
DROP DATABASE `dreamtimenews`;
CREATE DATABASE `dreamtimenews`;
USE dreamtimenews;

-- --------------------------------------------------------

-- 
-- 表的结构 `admin`
-- 

CREATE TABLE `admin` (
  `AdminID` int(11) NOT NULL auto_increment,
  `AdminName` varchar(32) default NULL,
  `AdminPwd` varchar(64) default NULL,
  `AdminType` smallint(6) default '0',
  `AddTime` varchar(20) default NULL,
  `LastLoginTime` varchar(50) default '暂无登录',
  `LastLoginIP` varchar(50) default '暂无登录',
  `NewsNum` int(11) default '0',
  `LoginNum` int(11) default '0',
  `UserName` varchar(20) default NULL,
  `UserSex` char(2) default NULL,
  `UserBirthday` varchar(10) default NULL,
  `UserEmail` varchar(50) default NULL,
  `UserQQ` varchar(10) default NULL,
  `UserTel` varchar(50) default NULL,
  `UserAddress` varchar(80) default NULL,
  `UserZip` varchar(6) default NULL,
  `UserInfo` longtext,
  PRIMARY KEY  (`AdminID`),
  KEY `AdminUID` (`AdminName`),
  KEY `LoginNum` (`LoginNum`),
  KEY `NewsNum` (`NewsNum`)
) TYPE=MyISAM AUTO_INCREMENT=5 ;

-- 
-- 导出表中的数据 `admin`
-- 

 
INSERT INTO `admin` (`AdminID`, `AdminName`, `AdminPwd`, `AdminType`, `AddTime`, `LastLoginTime`, `LastLoginIP`, `NewsNum`, `LoginNum`, `UserName`, `UserSex`, `UserBirthday`, `UserEmail`, `UserQQ`, `UserTel`, `UserAddress`, `UserZip`, `UserInfo`) VALUES (3, 'admin', 'CB939D9EA1198112BF8EDCD343ED2A1D', 2, '2006-3-20 15:36:02', '2006-3-21 10:38:10', '172.16.165.50', 0, 1, 'admin', '男', '1983-2-29', 'fanwsp@126.com', '', '', '', '', '');

-- --------------------------------------------------------

-- 
-- 表的结构 `bigclass`
-- 

CREATE TABLE `bigclass` (
  `BigClassID` smallint(6) NOT NULL default '0',
  `BigClassName` varchar(30) default NULL,
  `BigClassInfo` varchar(200) default NULL,
  `AddTime` datetime default NULL,
  PRIMARY KEY  (`BigClassID`),
  KEY `ClassID` (`BigClassID`)
) TYPE=MyISAM;

-- 
-- 导出表中的数据 `bigclass`
-- 

-- 
-- 表的结构 `config`
-- 

CREATE TABLE `config` (
  `ConfigID` int(11) NOT NULL auto_increment,
  `AdminUserListNum` smallint(6) default '0',
  `AdminLogListNum` smallint(6) default '0',
  `AdminNewsListNum` smallint(6) default '0',
  `HotNewsNum` smallint(6) default '0',
  `HeadNewsNum` smallint(6) default '0',
  `TopNewsNum` smallint(6) default '0',
  `TopImgNum` smallint(6) default '0',
  `ClassNewsNum` smallint(6) default '0',
  `ClassImgNum` smallint(6) default '0',
  `SpecNum` smallint(6) default '0',
  `SpecNewsNum` smallint(6) default '0',
  `BHotNewsNum` smallint(6) default '0',
  `BHeadNewsNum` smallint(6) default '0',
  `BTopNewsNum` smallint(6) default '0',
  `BTopImgNum` smallint(6) default '0',
  `BClassNewsNum` smallint(6) default '0',
  `BClassImgNum` smallint(6) default '0',
  `BSpecNum` smallint(6) default '0',
  `BSpecNewsNum` smallint(6) default '0',
  `ListSpecNum` smallint(6) default '0',
  `ListNewsNum` smallint(6) default '0',
  `SearchNewsNum` smallint(6) default '0',
  `DreamNewsTitle` varchar(30) default NULL,
  `DreamNewsCopyRight` varchar(30) default NULL,
  `DreamNewsEmail` varchar(30) default NULL,
  PRIMARY KEY  (`ConfigID`),
  KEY `AdminClassListNum` (`AdminLogListNum`),
  KEY `AdminNewsListNum` (`AdminNewsListNum`),
  KEY `ClassImgNum` (`ClassImgNum`),
  KEY `ClassImgNum1` (`BClassImgNum`),
  KEY `ClassNewsNum` (`ClassNewsNum`),
  KEY `ClassNewsNum1` (`BClassNewsNum`),
  KEY `HeadNewsNum` (`HeadNewsNum`),
  KEY `HeadNewsNum1` (`BHeadNewsNum`),
  KEY `HotNewsNum` (`BHotNewsNum`),
  KEY `ListNewsNum` (`ListNewsNum`),
  KEY `ListSpecNum` (`ListSpecNum`),
  KEY `MarqueeNewsNum` (`HotNewsNum`),
  KEY `SearchNewsNum` (`SearchNewsNum`),
  KEY `SpecNewsNum` (`SpecNewsNum`),
  KEY `SpecNewsNum1` (`BSpecNewsNum`),
  KEY `SpecNum` (`SpecNum`),
  KEY `SpecNum1` (`BSpecNum`),
  KEY `TopImgNum` (`TopImgNum`),
  KEY `TopImgNum1` (`BTopImgNum`),
  KEY `TopNewsNum` (`TopNewsNum`),
  KEY `TopNewsNum1` (`BTopNewsNum`),
  KEY `UserListNum` (`AdminUserListNum`)
) TYPE=MyISAM AUTO_INCREMENT=2 ;

-- 
-- 导出表中的数据 `config`
-- 

INSERT INTO `config` (`ConfigID`, `AdminUserListNum`, `AdminLogListNum`, `AdminNewsListNum`, `HotNewsNum`, `HeadNewsNum`, `TopNewsNum`, `TopImgNum`, `ClassNewsNum`, `ClassImgNum`, `SpecNum`, `SpecNewsNum`, `BHotNewsNum`, `BHeadNewsNum`, `BTopNewsNum`, `BTopImgNum`, `BClassNewsNum`, `BClassImgNum`, `BSpecNum`, `BSpecNewsNum`, `ListSpecNum`, `ListNewsNum`, `SearchNewsNum`, `DreamNewsTitle`, `DreamNewsCopyRight`, `DreamNewsEmail`) VALUES (1, 10, 50, 50, 8, 2, 10, 6, 10, 6, 10, 10, 4, 1, 10, 6, 10, 6, 8, 8, 10, 50, 50, '梦想年华新闻系统', '梦想年华[DreamTime]', 'fanwsp@126.com');

-- --------------------------------------------------------

-- 
-- 表的结构 `log`
-- 

CREATE TABLE `log` (
  `LogID` int(11) NOT NULL auto_increment,
  `User` varchar(32) default NULL,
  `LogType` varchar(100) default NULL,
  `LogTime` datetime default NULL,
  `IP` varchar(15) default NULL,
  `Result` char(3) default NULL,
  PRIMARY KEY  (`LogID`),
  KEY `LogID` (`LogID`)
) TYPE=MyISAM AUTO_INCREMENT=12 ;


-- 
-- 表的结构 `news`
-- 

CREATE TABLE `news` (
  `NewsID` int(11) NOT NULL auto_increment,
  `NewsTitle` varchar(60) default NULL,
  `NewsContent` longtext,
  `NewsKey` varchar(30) default NULL,
  `NewsAuthor` varchar(20) default NULL,
  `NewsFrom` varchar(60) default NULL,
  `NewsTime` varchar(50) default NULL,
  `NewsPicture` varchar(120) default NULL,
  `BigClassID` int(11) default '0',
  `SmallClassID` int(11) default '0',
  `IsHead` char(3) default NULL,
  `HeadPicture` varchar(120) default NULL,
  `IsImg` char(3) default NULL,
  `IsHot` char(3) default NULL,
  `SpecialID` int(11) default '0',
  `NewsInfo` varchar(200) default NULL,
  `AdminName` varchar(32) default NULL,
  PRIMARY KEY  (`NewsID`),
  KEY `BigClassID` (`BigClassID`),
  KEY `NewsID` (`NewsID`),
  KEY `NewsKey` (`NewsKey`),
  KEY `SmallClassID` (`SmallClassID`),
  KEY `SpecialID` (`SpecialID`)
) TYPE=MyISAM AUTO_INCREMENT=11 ;



-- 
-- 表的结构 `smallclass`
-- 

CREATE TABLE `smallclass` (
  `SmallClassID` int(11) NOT NULL auto_increment,
  `SmallClassName` varchar(30) default NULL,
  `SmallClassInfo` varchar(50) default NULL,
  `BigClassID` smallint(6) default '0',
  `AddTime` datetime default NULL,
  PRIMARY KEY  (`SmallClassID`),
  KEY `BigClassID` (`BigClassID`),
  KEY `ClassID` (`SmallClassID`)
) TYPE=MyISAM AUTO_INCREMENT=6 ;

-- 
-- 导出表中的数据 `smallclass`
-- 

INSERT INTO `smallclass` (`SmallClassID`, `SmallClassName`, `SmallClassInfo`, `BigClassID`, `AddTime`) VALUES (1, '时事政治', '理事政治', 1, '2006-03-13 21:30:42');
INSERT INTO `smallclass` (`SmallClassID`, `SmallClassName`, `SmallClassInfo`, `BigClassID`, `AddTime`) VALUES (2, '案件反腐', '案件反腐', 1, '2006-03-13 21:31:11');
INSERT INTO `smallclass` (`SmallClassID`, `SmallClassName`, `SmallClassInfo`, `BigClassID`, `AddTime`) VALUES (3, '港澳台新闻', '港澳台新闻', 1, '2006-03-13 21:31:35');
INSERT INTO `smallclass` (`SmallClassID`, `SmallClassName`, `SmallClassInfo`, `BigClassID`, `AddTime`) VALUES (4, '实用新闻', '实用新闻', 1, '2006-03-13 21:32:00');
INSERT INTO `smallclass` (`SmallClassID`, `SmallClassName`, `SmallClassInfo`, `BigClassID`, `AddTime`) VALUES (5, '事故灾难', '事故灾难', 1, '2006-03-13 21:33:03');

-- --------------------------------------------------------

-- 
-- 表的结构 `special`
-- 

CREATE TABLE `special` (
  `SpecialID` int(11) NOT NULL auto_increment,
  `SpecialName` varchar(80) default NULL,
  `SpecialInfo` varchar(200) default NULL,
  `SpecialTime` date default NULL,
  PRIMARY KEY  (`SpecialID`),
  KEY `SpecialID` (`SpecialID`)
) TYPE=MyISAM AUTO_INCREMENT=1 ;

        