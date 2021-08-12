USE [master]
GO
/****** Object:  Database [DB_Toko]    Script Date: 12/08/2021 14:49:50 ******/
CREATE DATABASE [DB_Toko]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DB_Toko', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\DB_Toko.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DB_Toko_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\DB_Toko_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DB_Toko] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DB_Toko].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DB_Toko] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DB_Toko] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DB_Toko] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DB_Toko] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DB_Toko] SET ARITHABORT OFF 
GO
ALTER DATABASE [DB_Toko] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [DB_Toko] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DB_Toko] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DB_Toko] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DB_Toko] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DB_Toko] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DB_Toko] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DB_Toko] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DB_Toko] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DB_Toko] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DB_Toko] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DB_Toko] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DB_Toko] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DB_Toko] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DB_Toko] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DB_Toko] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DB_Toko] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DB_Toko] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DB_Toko] SET  MULTI_USER 
GO
ALTER DATABASE [DB_Toko] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DB_Toko] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DB_Toko] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DB_Toko] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DB_Toko] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DB_Toko] SET QUERY_STORE = OFF
GO
USE [DB_Toko]
GO
/****** Object:  Table [dbo].[dt_aktivitas_produk]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dt_aktivitas_produk](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_akt] [int] NULL,
	[id_produk] [int] NULL,
	[jumlah] [int] NULL,
	[harga] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ms_dompet]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ms_dompet](
	[id_dompet] [int] IDENTITY(1,1) NOT NULL,
	[id_toko] [int] NULL,
	[uang] [int] NULL,
	[creaby] [varchar](30) NULL,
	[creadate] [datetime] NULL,
	[modiby] [varchar](max) NULL,
	[modidate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_dompet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ms_produk]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ms_produk](
	[id_produk] [int] IDENTITY(1,1) NOT NULL,
	[id_toko] [int] NULL,
	[nama] [varchar](100) NULL,
	[merk] [varchar](50) NULL,
	[harga] [int] NULL,
	[jumlah] [int] NULL,
	[foto] [varchar](max) NULL,
	[creaby] [varchar](30) NULL,
	[creadate] [datetime] NULL,
	[modiby] [varchar](max) NULL,
	[modidate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_produk] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ms_toko]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ms_toko](
	[id_toko] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](20) NULL,
	[password] [varchar](30) NULL,
	[nama_pemiliki] [varchar](50) NULL,
	[email] [varchar](30) NULL,
	[no_telfon] [varchar](13) NULL,
	[jenis_kelamin] [varchar](10) NULL,
	[tempat_lahir] [varchar](30) NULL,
	[tanggal_lahir] [date] NULL,
	[alamat] [varchar](max) NULL,
	[alamat_toko] [varchar](max) NULL,
	[NIK] [varchar](16) NULL,
	[foto_KTP] [varchar](max) NULL,
	[foto_diri] [varchar](max) NULL,
	[foto_toko] [varchar](max) NULL,
	[creaby] [varchar](30) NULL,
	[creadate] [datetime] NULL,
	[modiby] [varchar](max) NULL,
	[modidate] [datetime] NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_toko] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tr_aktivitas_dompet]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tr_aktivitas_dompet](
	[id_akt] [int] IDENTITY(1,1) NOT NULL,
	[id_dompet] [int] NULL,
	[kode_akt] [int] NULL,
	[jumlah] [int] NULL,
	[keterangan] [varchar](max) NULL,
	[creaby] [varchar](30) NULL,
	[creadate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_akt] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tr_aktivitas_produk]    Script Date: 12/08/2021 14:49:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tr_aktivitas_produk](
	[id_akt] [int] IDENTITY(1,1) NOT NULL,
	[kode_akt] [int] NULL,
	[jumlah] [int] NULL,
	[keterangan] [varchar](max) NULL,
	[creaby] [varchar](30) NULL,
	[creadate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_akt] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[ms_dompet] ON 

INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (1, 1, 100000, N'System', CAST(N'2021-08-12T13:35:11.400' AS DateTime), N'System', CAST(N'2021-08-12T14:15:16.277' AS DateTime))
INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (2, 2, 3000000, N'System', CAST(N'2021-08-12T13:38:02.197' AS DateTime), N'System', CAST(N'2021-08-12T14:27:57.863' AS DateTime))
INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (3, 3, 5000000, N'System', CAST(N'2021-08-12T13:45:53.020' AS DateTime), N'System', CAST(N'2021-08-12T14:31:00.693' AS DateTime))
INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (4, 4, 6000000, N'System', CAST(N'2021-08-12T13:46:38.427' AS DateTime), N'System', CAST(N'2021-08-12T14:33:46.530' AS DateTime))
INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (5, 5, 6000000, N'System', CAST(N'2021-08-12T13:48:40.700' AS DateTime), N'System', CAST(N'2021-08-12T14:39:08.250' AS DateTime))
INSERT [dbo].[ms_dompet] ([id_dompet], [id_toko], [uang], [creaby], [creadate], [modiby], [modidate]) VALUES (6, 6, 0, N'System', CAST(N'2021-08-12T14:48:53.133' AS DateTime), N'', CAST(N'2021-08-12T14:48:53.133' AS DateTime))
SET IDENTITY_INSERT [dbo].[ms_dompet] OFF
SET IDENTITY_INSERT [dbo].[ms_produk] ON 

INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (1, 1, N'Barang 1', N'Brand X', 5000, 5, N'', N'firda@gmail.com', CAST(N'2021-08-12T14:06:50.237' AS DateTime), N'', CAST(N'2021-08-12T14:06:50.237' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (2, 1, N'Barang 2', N'Brand X', 6000, 5, N'', N'firda@gmail.com', CAST(N'2021-08-12T14:07:09.463' AS DateTime), N'', CAST(N'2021-08-12T14:07:09.463' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (3, 1, N'Barang 3', N'Brand X', 6000, 5, N'', N'firda@gmail.com', CAST(N'2021-08-12T14:11:50.480' AS DateTime), N'', CAST(N'2021-08-12T14:11:50.480' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (4, 1, N'Barang 5', N'Brand X', 5000, 59, N'', N'firda@gmail.com', CAST(N'2021-08-12T14:14:05.543' AS DateTime), N'', CAST(N'2021-08-12T14:14:05.543' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (5, 1, N'Barang 4', N'Brand X', 4000, 40, N'', N'firda@gmail.com', CAST(N'2021-08-12T14:14:27.030' AS DateTime), N'', CAST(N'2021-08-12T14:14:27.030' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (6, 2, N'Mie Goreng', N'FoodWings', 3000, 50, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:16:16.227' AS DateTime), N'', CAST(N'2021-08-12T14:16:16.227' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (7, 2, N'Mie Goreng', N'Indoomie', 5000, 50, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:26:32.377' AS DateTime), N'', CAST(N'2021-08-12T14:26:32.377' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (8, 2, N'Mie Rebus', N'Indomie', 3000, 40, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:26:48.423' AS DateTime), N'', CAST(N'2021-08-12T14:26:48.423' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (9, 2, N'Mie Kuah', N'Indoofood', 4000, 30, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:27:05.830' AS DateTime), N'', CAST(N'2021-08-12T14:27:05.830' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (10, 2, N'Mie Ayam', N'Indoofood', 5000, 39, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:27:27.300' AS DateTime), N'', CAST(N'2021-08-12T14:27:27.300' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (11, 2, N'Mie Jamur', N'Indofood', 3000, 30, N'', N'salsa@gmail.com', CAST(N'2021-08-12T14:27:44.090' AS DateTime), N'', CAST(N'2021-08-12T14:27:44.090' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (12, 3, N'Roti bakar', N'Indofood', 3000, 30, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:28:33.207' AS DateTime), N'', CAST(N'2021-08-12T14:28:33.207' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (13, 3, N'Roti bakar', N'Indofood', 4000, 39, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:29:08.427' AS DateTime), N'', CAST(N'2021-08-12T14:29:08.427' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (14, 3, N'Roti rebus', N'Brand X', 5000, 30, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:29:37.643' AS DateTime), N'', CAST(N'2021-08-12T14:29:37.643' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (15, 3, N'Roti kukus', N'Roti''O', 5000, 46, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:30:03.790' AS DateTime), N'', CAST(N'2021-08-12T14:30:03.790' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (16, 3, N'Roti tawar', N'Roti''O', 5000, 30, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:30:19.507' AS DateTime), N'', CAST(N'2021-08-12T14:30:19.507' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (17, 3, N'Roti Cokelat', N'Roti''O', 4000, 30, N'', N'adit@gmail.com', CAST(N'2021-08-12T14:30:45.537' AS DateTime), N'', CAST(N'2021-08-12T14:30:45.537' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (18, 4, N'Beras biasa', N'Beras X', 10000, 40, N'', N'anggi@gmail.com', CAST(N'2021-08-12T14:31:42.347' AS DateTime), N'', CAST(N'2021-08-12T14:31:42.347' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (19, 4, N'Beras bulog', N'Beras X', 6000, 60, N'', N'anggi@gmail.com', CAST(N'2021-08-12T14:32:16.270' AS DateTime), N'', CAST(N'2021-08-12T14:32:16.270' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (20, 4, N'Beras mutiara', N'Beras X', 5000, 50, N'', N'anggi@gmail.com', CAST(N'2021-08-12T14:32:44.767' AS DateTime), N'', CAST(N'2021-08-12T14:32:44.767' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (21, 4, N'Beras kuning', N'Beras X6000', 9000, 70, N'', N'anggi@gmail.com', CAST(N'2021-08-12T14:33:17.410' AS DateTime), N'', CAST(N'2021-08-12T14:33:17.410' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (22, 4, N'Beras merah', N'Berads X', 7000, 50, N'', N'anggi@gmail.com', CAST(N'2021-08-12T14:33:33.700' AS DateTime), N'', CAST(N'2021-08-12T14:33:33.700' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (23, 5, N'Susu', N'ABC', 4000, 50, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:34:20.300' AS DateTime), N'', CAST(N'2021-08-12T14:34:20.300' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (24, 5, N'Susu 1L', N'ABC', 7000, 28, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:34:41.470' AS DateTime), N'', CAST(N'2021-08-12T14:34:41.470' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (25, 5, N'Susu 10L', N'ABC', 90000, 10, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:34:58.197' AS DateTime), N'', CAST(N'2021-08-12T14:34:58.197' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (26, 5, N'Sirup Saschet', N'ABC', 9000, 50, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:35:23.060' AS DateTime), N'', CAST(N'2021-08-12T14:35:23.060' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (27, 5, N'Sirup Merah', N'Marjan', 25000, 20, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:35:50.910' AS DateTime), N'', CAST(N'2021-08-12T14:35:50.910' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (28, 5, N'Sirup Hijau', N'Marjanjan', 22000, 20, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:36:17.600' AS DateTime), N'', CAST(N'2021-08-12T14:36:17.600' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (29, 5, N'Chicki Taro', N'Brand X', 4500, 20, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:37:33.683' AS DateTime), N'', CAST(N'2021-08-12T14:37:33.683' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (30, 5, N'Chicki Jaguar', N'ABCx', 4000, 50, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:37:56.440' AS DateTime), N'', CAST(N'2021-08-12T14:37:56.440' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (31, 5, N'Procol', N'ObatX', 4500, 55, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:38:24.213' AS DateTime), N'', CAST(N'2021-08-12T14:38:24.213' AS DateTime))
INSERT [dbo].[ms_produk] ([id_produk], [id_toko], [nama], [merk], [harga], [jumlah], [foto], [creaby], [creadate], [modiby], [modidate]) VALUES (32, 5, N'Konidin', N'AbCx', 4000, 50, N'', N'teddy@gmail.com', CAST(N'2021-08-12T14:38:49.783' AS DateTime), N'', CAST(N'2021-08-12T14:38:49.783' AS DateTime))
SET IDENTITY_INSERT [dbo].[ms_produk] OFF
SET IDENTITY_INSERT [dbo].[ms_toko] ON 

INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (1, N'Toko1', N'123', N'Toko Firda', N'firda@gmail.com', N'089560497', N'0', N'Jakarta', CAST(N'2000-08-12' AS Date), N'Bekasi', N'Tambun, Bekasi', N'8726671366712379', N'', N'', N'', N'firda@gmail.com', CAST(N'2021-08-12T13:35:11.400' AS DateTime), N'', CAST(N'2021-08-12T13:35:11.400' AS DateTime), 1)
INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (2, N'Toko2', N'123', N'Toko Salsa', N'salsa@gmail.com', N'089652635', N'0', N'Jakarta', CAST(N'2003-08-12' AS Date), N'Jakarta Utara', N'Kemayoran Jakarta Utara', N'0897174872', N'', N'', N'', N'salsa@gmail.com', CAST(N'2021-08-12T13:38:02.197' AS DateTime), N'', CAST(N'2021-08-12T13:38:02.197' AS DateTime), 1)
INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (3, N'Toko3', N'123', N'Toko Adit', N'adit@gmail.com', N'0895623124', N'1', N'Jakarta', CAST(N'2009-08-10' AS Date), N'Jakarta', N'Sikayu Comal Pemalang', N'08952747812', N'', N'', N'', N'adit@gmail.com', CAST(N'2021-08-12T13:45:53.020' AS DateTime), N'adit@gmail.com', CAST(N'2021-08-12T14:45:33.147' AS DateTime), 1)
INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (4, N'Toko4', N'123', N'Toko Anggi', N'anggi@gmail.com', N'089275616382', N'1', N'Surabaya', CAST(N'2011-08-12' AS Date), N'Surabaya', N'Surabaya', N'987637162746', N'', N'', N'', N'anggi@gmail.com', CAST(N'2021-08-12T13:46:38.427' AS DateTime), N'', CAST(N'2021-08-12T13:46:38.427' AS DateTime), 1)
INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (5, N'Toko5', N'123', N'Toko Teddy', N'teddy@gmail.com', N'08952716736', N'1', N'Pemalang', CAST(N'2005-08-11' AS Date), N'Pemalang', N'Kauman Comal Pemalang', N'222716367162', N'', N'', N'', N'teddy@gmail.com', CAST(N'2021-08-12T13:48:40.700' AS DateTime), N'teddy@gmail.com', CAST(N'2021-08-12T14:40:02.830' AS DateTime), 1)
INSERT [dbo].[ms_toko] ([id_toko], [username], [password], [nama_pemiliki], [email], [no_telfon], [jenis_kelamin], [tempat_lahir], [tanggal_lahir], [alamat], [alamat_toko], [NIK], [foto_KTP], [foto_diri], [foto_toko], [creaby], [creadate], [modiby], [modidate], [status]) VALUES (6, N'Toko6', N'123', N'Ivan Toko', N'ivan@gmail.com', N'08956063234', N'1', N'Pemalang', CAST(N'2000-08-12' AS Date), N'Pamutih', N'Pamutih Ulujami Pemalang', N'982718231', N'', N'', N'', N'ivan@gmail.com', CAST(N'2021-08-12T14:48:53.133' AS DateTime), N'', CAST(N'2021-08-12T14:48:53.133' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[ms_toko] OFF
SET IDENTITY_INSERT [dbo].[tr_aktivitas_dompet] ON 

INSERT [dbo].[tr_aktivitas_dompet] ([id_akt], [id_dompet], [kode_akt], [jumlah], [keterangan], [creaby], [creadate]) VALUES (1, 1, 3, 100000, N'Perubahan jumlah pada kasir', N'System', CAST(N'2021-08-12T14:15:16.277' AS DateTime))
INSERT [dbo].[tr_aktivitas_dompet] ([id_akt], [id_dompet], [kode_akt], [jumlah], [keterangan], [creaby], [creadate]) VALUES (2, 2, 3, 3000000, N'Perubahan jumlah pada kasir', N'System', CAST(N'2021-08-12T14:27:57.863' AS DateTime))
INSERT [dbo].[tr_aktivitas_dompet] ([id_akt], [id_dompet], [kode_akt], [jumlah], [keterangan], [creaby], [creadate]) VALUES (3, 3, 3, 5000000, N'Perubahan jumlah pada kasir', N'System', CAST(N'2021-08-12T14:31:00.693' AS DateTime))
INSERT [dbo].[tr_aktivitas_dompet] ([id_akt], [id_dompet], [kode_akt], [jumlah], [keterangan], [creaby], [creadate]) VALUES (4, 4, 3, 6000000, N'Perubahan jumlah pada kasir', N'System', CAST(N'2021-08-12T14:33:46.530' AS DateTime))
INSERT [dbo].[tr_aktivitas_dompet] ([id_akt], [id_dompet], [kode_akt], [jumlah], [keterangan], [creaby], [creadate]) VALUES (5, 5, 3, 6000000, N'Perubahan jumlah pada kasir', N'System', CAST(N'2021-08-12T14:39:08.250' AS DateTime))
SET IDENTITY_INSERT [dbo].[tr_aktivitas_dompet] OFF
USE [master]
GO
ALTER DATABASE [DB_Toko] SET  READ_WRITE 
GO
