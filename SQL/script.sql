USE [master]
GO
/****** Object:  Database [phoneshop]    Script Date: 7/26/2016 10:30:36 AM ******/
CREATE DATABASE [phoneshop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'phoneshop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\phoneshop.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'phoneshop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\phoneshop_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [phoneshop] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [phoneshop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [phoneshop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [phoneshop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [phoneshop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [phoneshop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [phoneshop] SET ARITHABORT OFF 
GO
ALTER DATABASE [phoneshop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [phoneshop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [phoneshop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [phoneshop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [phoneshop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [phoneshop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [phoneshop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [phoneshop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [phoneshop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [phoneshop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [phoneshop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [phoneshop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [phoneshop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [phoneshop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [phoneshop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [phoneshop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [phoneshop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [phoneshop] SET RECOVERY FULL 
GO
ALTER DATABASE [phoneshop] SET  MULTI_USER 
GO
ALTER DATABASE [phoneshop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [phoneshop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [phoneshop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [phoneshop] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [phoneshop]
GO
/****** Object:  Table [dbo].[authorities]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[authorities](
	[username] [varchar](50) NOT NULL,
	[authority] [varchar](50) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[directory_navigations]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[directory_navigations](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type_navigation] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_directory_navigations] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[phone_navigation]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phone_navigation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[phone_id] [int] NOT NULL,
	[navigation_id] [int] NOT NULL,
 CONSTRAINT [PK_phone_navigation] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[phone_wireless]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phone_wireless](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[phone_id] [int] NOT NULL,
	[wireless_id] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[phones]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phones](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[model] [nvarchar](250) NOT NULL,
	[brand] [nvarchar](150) NOT NULL,
	[os] [nvarchar](max) NULL,
	[screen_resolution] [nvarchar](50) NULL,
	[price] [money] NULL,
	[screen_size] [numeric](18, 0) NULL,
	[camera_resolution] [nvarchar](150) NULL,
	[cpu] [nvarchar](150) NULL,
	[core_number] [smallint] NULL,
	[memory_in] [int] NULL,
	[ram] [int] NULL,
	[weight] [numeric](18, 0) NULL,
	[height] [numeric](18, 0) NULL,
	[width] [numeric](18, 0) NULL,
	[depth] [numeric](18, 0) NULL,
	[sim_type] [nvarchar](50) NULL,
	[sim_number] [int] NULL,
 CONSTRAINT [PK_phones] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[users]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[enabled] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wireless_technology]    Script Date: 7/26/2016 10:30:36 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[wireless_technology](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[technology] [nvarchar](50) NULL,
 CONSTRAINT [PK_wireless_technology] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [ix_auth_username]    Script Date: 7/26/2016 10:30:36 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [ix_auth_username] ON [dbo].[authorities]
(
	[username] ASC,
	[authority] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[authorities]  WITH CHECK ADD  CONSTRAINT [fk_authorities_users] FOREIGN KEY([username])
REFERENCES [dbo].[users] ([username])
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [fk_authorities_users]
GO
ALTER TABLE [dbo].[phone_navigation]  WITH CHECK ADD  CONSTRAINT [FK_phone_navigation_directory_navigations] FOREIGN KEY([navigation_id])
REFERENCES [dbo].[directory_navigations] ([id])
GO
ALTER TABLE [dbo].[phone_navigation] CHECK CONSTRAINT [FK_phone_navigation_directory_navigations]
GO
ALTER TABLE [dbo].[phone_navigation]  WITH CHECK ADD  CONSTRAINT [FK_phone_navigation_phones] FOREIGN KEY([phone_id])
REFERENCES [dbo].[phones] ([id])
GO
ALTER TABLE [dbo].[phone_navigation] CHECK CONSTRAINT [FK_phone_navigation_phones]
GO
ALTER TABLE [dbo].[phone_wireless]  WITH CHECK ADD  CONSTRAINT [FK_phone_wireless_phones] FOREIGN KEY([phone_id])
REFERENCES [dbo].[phones] ([id])
GO
ALTER TABLE [dbo].[phone_wireless] CHECK CONSTRAINT [FK_phone_wireless_phones]
GO
ALTER TABLE [dbo].[phone_wireless]  WITH CHECK ADD  CONSTRAINT [FK_phone_wireless_wireless_technology] FOREIGN KEY([wireless_id])
REFERENCES [dbo].[wireless_technology] ([id])
GO
ALTER TABLE [dbo].[phone_wireless] CHECK CONSTRAINT [FK_phone_wireless_wireless_technology]
GO
USE [master]
GO
ALTER DATABASE [phoneshop] SET  READ_WRITE 
GO
