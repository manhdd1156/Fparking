-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 103.81.84.53    Database: fparking
-- ------------------------------------------------------
-- Server version	5.6.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(11) DEFAULT '1',
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','fcea920f7412b5da7be0cf42b8c93759','Bùi Quang Hưng','hungbqse04029@fpt.edu.vn',1,1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `drivervehicle_id` int(11) NOT NULL,
  `parking_id` int(11) NOT NULL,
  `timein` datetime DEFAULT NULL,
  `timeout` datetime DEFAULT NULL,
  `price` double NOT NULL,
  `status` int(11) NOT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `comission` double NOT NULL,
  `totalfine` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `parking_booking_idFK_idx` (`parking_id`),
  KEY `drivervihicle_idFK_idx` (`drivervehicle_id`),
  CONSTRAINT `drivervihicle_idFK` FOREIGN KEY (`drivervehicle_id`) REFERENCES `driver_vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `parking_booking_idFK` FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,1,'2018-07-01 01:00:00','2018-07-25 03:31:40',10000,3,100000,0.05,0),(2,2,2,'2018-07-01 01:00:00','2018-08-01 08:00:00',10000,3,100000,0.5,0),(3,3,3,'2018-07-17 11:43:18','2018-08-17 11:43:28',10000,3,100000,0.5,0),(4,4,4,'2018-07-01 06:00:00','2018-08-01 12:00:00',10000,3,100000,0.5,0),(5,5,5,'2018-07-24 10:00:00','2018-07-24 13:00:00',75000,3,100000,0.5,0),(6,6,6,'2018-07-24 15:00:00','2018-08-11 19:00:00',50000,3,150000,0.5,0),(7,7,7,'2018-07-24 10:00:00','2018-08-11 13:00:00',75000,3,100000,0.5,0),(8,8,8,'2018-07-24 15:00:00','2018-08-11 19:00:00',50000,3,150000,0.5,0),(9,9,9,'2018-07-23 00:00:00','2018-08-11 03:00:00',50000,3,200000,0.5,0),(10,10,17,'2018-08-10 00:39:50','2018-08-10 00:44:48',10000,3,84200,0.05,74200),(11,11,18,'2018-08-10 01:01:26','2018-08-10 01:10:34',10000,3,52400,0.05,42400),(12,12,19,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(13,13,20,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(14,14,21,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(15,15,1,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(16,16,23,'2018-08-11 22:34:08','2018-08-11 22:34:41',10000,3,31200,0.05,21200),(17,17,24,'2018-08-11 22:39:06','2018-08-12 22:39:32',10000,3,31200,0.05,21200),(18,18,25,'2018-08-11 22:40:38','2018-08-12 22:40:51',10000,3,31200,0.05,21200),(19,19,26,'2018-08-13 23:19:29','2018-08-13 23:24:53',10000,3,10000,0.05,0),(20,20,27,'2018-08-13 23:58:17','2018-08-13 23:58:37',10000,3,10000,0.05,0),(21,21,28,'2018-08-14 00:09:45','2018-08-14 00:09:54',10000,3,10000,0.05,0),(22,22,29,'2018-08-14 01:42:50','2018-08-14 01:43:02',10000,3,10000,0.05,0),(23,23,30,'2018-08-14 01:48:46','2018-08-14 01:48:51',10000,3,10000,0.05,0),(24,24,31,'2018-08-15 01:40:22','2018-08-21 21:14:45',10000,3,211629.7527777778,0.05,15900),(25,25,41,'2018-08-15 21:30:12','2018-08-21 21:30:20',10000,3,25900,0.05,15900),(26,1,1,'2018-08-15 21:34:21','2018-08-15 21:34:23',10000,3,25900,0.05,15900),(27,2,2,'2018-08-15 21:36:26','2018-08-15 21:36:28',10000,3,25900,0.05,15900),(28,3,3,'2018-08-15 21:38:27','2018-08-15 21:38:29',10000,3,25900,0.05,15900),(29,4,4,'2018-08-15 21:40:54','2018-08-15 21:40:57',10000,3,25900,0.05,15900),(30,5,5,'2018-08-15 22:09:14','2018-08-15 22:09:16',10000,3,25900,0.05,15900),(31,6,6,'2018-08-15 22:09:44','2018-08-15 22:10:14',10000,3,25900,0.05,15900),(32,7,7,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,5,0,0,0),(33,8,8,'2018-08-28 01:13:08','2018-08-28 01:13:15',10000,3,25900,0.1,15900),(34,9,9,'2018-08-28 01:15:39','2018-08-28 01:16:07',10000,3,25900,0.1,15900),(35,10,17,'2018-08-28 01:19:01','2018-08-28 01:19:07',10000,3,25900,0.1,15900),(36,11,18,'2018-08-28 01:21:53','2018-08-28 01:22:00',10000,3,25900,0.1,15900),(37,12,19,'2018-08-28 01:27:55','2018-08-28 01:28:06',10000,3,25900,0.1,15900),(38,13,20,'2018-08-28 01:32:40','2018-08-28 01:36:45',10000,3,25900,0.1,15900),(39,14,21,'2018-08-28 01:46:13','2018-08-28 01:46:20',10000,3,20600,0.1,10600),(40,15,22,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(41,16,23,'0000-00-00 00:00:00','0000-00-00 00:00:00',0,0,0,0,0),(42,17,24,'2018-08-28 01:50:17','2018-08-28 01:50:58',10000,3,15300,0.1,5300),(43,18,25,'2018-08-28 01:51:19','2018-08-28 01:51:24',10000,3,10000,0.1,0),(44,19,26,'2018-08-28 01:54:14','2018-08-28 01:54:17',10000,3,10000,0.1,0),(45,20,27,'2018-08-28 01:54:30','2018-08-28 01:54:32',10000,3,10000,0.1,0);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Hà Nội'),(2,'Hồ Chí Minh'),(3,'Hưng Yên'),(4,'Thanh Hóa'),(5,'Sơn La');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commision`
--

DROP TABLE IF EXISTS `commision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commision` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commision` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commision`
--

LOCK TABLES `commision` WRITE;
/*!40000 ALTER TABLE `commision` DISABLE KEYS */;
INSERT INTO `commision` VALUES (2,0.1);
/*!40000 ALTER TABLE `commision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (2,'Nguyễn Hồng Ngọc ','01665195810','e10adc3949ba59abbe56e057f20f883e',0),(3,'Nguyễn Đăng Khôi','01288028666','e10adc3949ba59abbe56e057f20f883e',1),(4,'Đinh Duy Mạnh','01665195809','2ff631ea76b0ea30a4b9abead8088674',1),(5,'Nguyễn Hữu Trung','01665195808','4297f44b13955235245b2497399d7a93',1),(6,'Trần Thái Sơn','01555195802','4297f44b13955235245b2497399d7a93',0),(7,'Nguyễn Hưu Quỳnh','0123456789','827ccb0eea8a706c4c34a16891f84e7b',1),(8,'Thái Đăng Long','0987654321','827ccb0eea8a706c4c34a16891f84e7b',0),(9,'Đỗ Minh Long','0968949064','25d55ad283aa400af464c76d713c07ad',0),(10,'Lưu Đức Hoa','0147258369','827ccb0eea8a706c4c34a16891f84e7b',0),(18,'Phùng Thị Anh Nguyệt','01210166519','25d55ad283aa400af464c76d713c07ad',1),(19,'Nguyễn Duy Anh','01647383196','2ff631ea76b0ea30a4b9abead8088674',1),(22,'Nguyễn Quang Hải','0975131548','4297f44b13955235245b2497399d7a93',1);
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver_vehicle`
--

DROP TABLE IF EXISTS `driver_vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver_vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `driver_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `driver_vehicle_idFK_idx` (`driver_id`),
  KEY `vehicle_driver_idFK_idx` (`vehicle_id`),
  CONSTRAINT `driver_vehicle_idFK` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vehicle_driver_idFK` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_vehicle`
--

LOCK TABLES `driver_vehicle` WRITE;
/*!40000 ALTER TABLE `driver_vehicle` DISABLE KEYS */;
INSERT INTO `driver_vehicle` VALUES (1,2,1,1),(2,3,2,1),(3,4,3,1),(4,5,4,1),(5,6,5,1),(6,7,15,1),(7,8,17,1),(8,9,18,1),(9,10,19,1),(10,18,20,1),(11,19,21,1),(12,22,22,1),(13,2,23,0),(14,3,24,0),(15,4,25,0),(16,5,26,0),(17,6,27,0),(18,7,28,0),(19,8,1,0),(20,9,2,0),(21,10,3,0),(22,18,4,0),(23,19,5,0),(24,22,15,0),(25,2,17,1),(26,3,18,1),(27,4,19,1),(28,5,20,1),(29,6,21,1),(30,7,22,1),(31,8,23,1),(32,9,24,1),(33,10,25,1),(34,18,26,1),(35,19,27,1),(36,22,28,1),(37,2,1,1),(38,3,2,1),(39,4,3,1),(40,5,4,1),(41,6,5,1),(42,7,15,1),(43,8,17,1),(44,9,18,1),(45,10,19,1),(46,18,20,1),(47,19,21,1),(48,22,22,1),(49,2,23,1),(50,3,24,1),(51,4,25,1),(52,5,26,1),(53,6,27,1),(54,7,28,1);
/*!40000 ALTER TABLE `driver_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `type` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `resolve` longtext COLLATE utf8mb4_unicode_ci,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='									';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'Nguyễn Hồng Ngọc ','01665195810','Có lỗi xảy ra khi đặt chỗ xong, app không hiển thị dẫn đường',1,'2018-07-31','Đã gửi lỗi cho đội bảo trì',1),(2,'Nguyễn Đăng Khôi','01288028666','Không có nút vị trí của tôi',1,'2018-08-01','Do chưa bật GPS',1),(3,'Nguyễn Hữu Trung','01665195808','Nhân viên của bãi xe ở sân tập lái xe Hòa Lạc có thái độ với khách',1,'2018-07-30','Đã gửi feedback cho chủ bãi',1),(4,'Trần Thái Sơn','01555195802','App chậm, chưa load được nhanh',1,'2018-08-01','Đã gửi yêu cầu cho đội bảo trì ( có thể do mạng chậm )',1),(5,'Thái Đăng Long','0987654321','Ứng dụng dở tệ!',1,'2018-07-29','',2),(6,'Lưu Đức Hoa','0147258369','Cần thêm chức năng hiển thị bãi xe lòng đường hay dưới hầm',1,'2018-07-31','Đã lưu vào bản cập nhật',1),(7,'Phùng Thị Anh Nguyệt','01210166519','Rất tốt!',1,'2018-08-08','',1),(8,'Nguyễn Duy Anh','01647383196','Bãi xe Khâm Thiên hiển thị trên ứng dụng nhưng lại không có trong thực tế',1,'2018-08-08','Đã gửi lỗi cho đội bảo trì',1),(9,'Nguyễn Quang Hải','0975131548','ứng dụng rất hay',1,'2018-08-08','',0),(10,'Nguyễn Đăng Khôi','01288028666','ueue7e7e6',0,'2018-08-09','Spam => trash',1),(11,'Nguyễn Đăng Khôi','01288028666','gêwgwegwegwegwegwgewgwegwegwegwegwegwe',0,'2018-08-09','Spam => trash',1),(12,'Nguyễn Đăng Khôi','01288028666','gewgwgdsdf332dvcx',0,'2018-08-09','Spam => trash',1);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `drivervehicle_id` int(11) NOT NULL,
  `parking_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `type` int(11) NOT NULL,
  `price` double NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fine_parking_idFK_idx` (`parking_id`),
  KEY `drivervehicle_idFK_idx` (`drivervehicle_id`),
  CONSTRAINT `drivervehicle_idFK` FOREIGN KEY (`drivervehicle_id`) REFERENCES `driver_vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fine_parking_idFK` FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
INSERT INTO `fine` VALUES (1,2,1,'2018-08-13 21:44:41',1,5000,0),(2,2,2,'2018-08-13 22:03:30',1,5000,1),(3,2,3,'2018-08-13 22:10:08',1,5000,0),(4,1,4,'2018-08-13 22:12:43',1,5000,0),(5,2,5,'2018-08-13 22:15:19',1,5000,0),(6,3,6,'2018-07-27 22:22:42',1,10000,0),(7,4,7,'2018-08-14 22:26:59',1,7000,0),(8,5,8,'2018-08-11 22:34:17',1,5000,0),(9,6,9,'2018-08-13 22:38:44',1,5000,0),(10,7,17,'2018-08-13 22:45:27',1,5000,0),(11,8,18,'2018-08-13 22:50:19',1,10000,0),(12,9,19,'2018-08-13 22:53:58',1,5000,0),(13,10,20,'2018-08-13 22:55:38',1,7000,0),(14,11,21,'2018-08-13 23:00:32',1,7000,0),(15,12,22,'2018-08-13 23:01:10',1,10000,0),(16,13,23,'2018-08-13 23:03:49',1,7000,0),(17,14,24,'2018-08-08 23:15:10',1,5000,0),(18,15,25,'2018-08-28 01:48:08',1,7000,0),(19,16,26,'2018-08-28 01:49:33',1,5000,0),(20,17,27,'2018-08-13 22:45:27',1,7000,0),(21,18,28,'2018-08-13 22:38:44',1,7000,0),(22,19,29,'2018-08-13 22:50:19',1,5000,0),(23,20,30,'2018-08-14 22:26:59',1,5000,0),(24,21,31,'2018-08-14 22:26:59',1,10000,0),(25,22,41,'2018-08-11 22:34:17',1,7000,0),(26,23,1,'2018-08-13 22:45:27',0,5000,1),(27,24,2,'2018-08-13 22:10:08',0,5000,1),(28,25,3,'2018-07-27 22:22:42',0,5000,1),(29,2,4,'2018-08-13 22:03:30',0,5000,1),(30,2,5,'2018-08-13 22:03:30',0,5000,1);
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finetariff`
--

DROP TABLE IF EXISTS `finetariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finetariff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `vehicletype_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicletype_idFK_idx` (`vehicletype_id`),
  CONSTRAINT `vehicletype_idFK` FOREIGN KEY (`vehicletype_id`) REFERENCES `vehicletype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finetariff`
--

LOCK TABLES `finetariff` WRITE;
/*!40000 ALTER TABLE `finetariff` DISABLE KEYS */;
INSERT INTO `finetariff` VALUES (1,5000,1),(2,7000,2),(3,10000,3);
/*!40000 ALTER TABLE `finetariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `driver_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `parking_id` int(11) NOT NULL,
  `event` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(11) NOT NULL,
  `data` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=807 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,'Đinh Duy Mạnh','0968949064','d8578edf8458ce06fbc5bb76a58c5ca4','125 HOÀNG Ngân'),(3,'Nguyễn Văn Hưng','01665195807','d8578edf8458ce06fbc5bb76a58c5ca4','27 Cầu Giấy'),(4,'Nguyễn Đăng Khôi','01665195806','d8578edf8458ce06fbc5bb76a58c5ca4','17 Xuân Thủy 123'),(5,'Nguyễn Xuân Phú','01643962125','d8578edf8458ce06fbc5bb76a58c5ca4','Quất Lâm-Nam Định'),(6,'Bùi Quang Hưng','0962548214','0a6c1a85c675f6817e725e4f3a41ef1f','33 Nguyễn Khánh Toàn-Quan Hoa-Cầu Giấy-Hà Nội'),(7,'Âu Văn Thịnh','0967239959','0a6c1a85c675f6817e725e4f3a41ef1f','Long Biên-Hà Nội'),(8,'Nguyễn Minh Hiếu','0935684872','fe4d36fb3827d314b6e48d2ffc601833','Trung Sơn Sầm Sơn Thanh Hoá'),(9,'Nguyễn Dũng Việt Anh','0988458123','fcea920f7412b5da7be0cf42b8c93759','Ba Vì Hà Nội');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parking`
--

DROP TABLE IF EXISTS `parking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `city_id` bigint(20) NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `latitude` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `longitude` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currentspace` int(11) NOT NULL DEFAULT '0',
  `totalspace` int(11) NOT NULL,
  `timeoc` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` longtext COLLATE utf8mb4_unicode_ci,
  `deposits` double NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_parking_idFK_idx` (`owner_id`),
  KEY `city_parking_idFK_idx` (`city_id`),
  CONSTRAINT `city_parking_idFK` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `owner_parking_idFK` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking`
--

LOCK TABLES `parking` WRITE;
/*!40000 ALTER TABLE `parking` DISABLE KEYS */;
INSERT INTO `parking` VALUES (1,1,1,'Sân tập lái xe ô tô Hòa Lạc','21.011246','105.524545',1,30,'07:00-24:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',1000000,1),(2,3,1,'Bãi đỗ xe trường đại học FPT','21.014545','105.5245456',90,90,'06:25-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',1000000,1),(3,4,1,'Bãi đậu xe Viettel 123','21.009576','105.531013',31,40,'07:00-19:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',1500000,1),(4,5,1,'FPT software villa 2','21.0101','105.535162',0,100,'09:00-20:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',10000000,1),(5,6,1,'BigC Thăng Long','21.007423','105.793423',29,30,'12:00-24:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',110000,1),(6,7,1,'Grand Plaza Hà Nội','21.007593','105.796824',50,50,'07:25-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',2030000,1),(7,8,3,'Ân Thi, Hưng Yên, Việt Nam','20.818088','106.061566',0,30,'05:30-23:20h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',200000,1),(8,9,5,'Bệnh Viện Đa Khoa Huyện Yên Châu','21.049912','104.307887',0,30,'05:30-23:20h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',500000,1),(9,1,4,'Bắc Sơn\r\nSầm Sơn\r\nThanh Hoá','19.743063','105.904455',0,87,'05:00-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',250000,1),(17,3,2,'Công viên Lê Thị Riêng','10.785094','106.664585',2,20,'07:20-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',2000000,6),(18,4,2,'Tân Phú, Hồ Chí Minh, Việt Nam','10.808897','106.615927',5,25,'07:30-21:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',2000000,6),(19,5,2,'Tp. Biên Hòa, Đồng Nai, Việt Nam','10.928869','106.805591',4,30,'07:40-21:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',500000,6),(20,6,2,'Trung tâm mua sắm Studio Bich','10.940018','106.864553',25,25,'06:40-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',200000,6),(21,7,4,'Gần bãi biển Hải Tiến','19.846199','105.936630',25,60,'06:40-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',250000,6),(22,8,4,'Bãi biển Hoàng Thanh','19.829282','105.932336',1,30,'06:40-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',3000000,6),(23,9,4,'Ba Đình, Thành phố Thanh Hóa','19.803370','105.777790',1,50,'06:40-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',1000000,6),(24,1,3,'Khu đô thị Ecopark, Văn Giang, Hưng Yên','20.966893','105.929796',2,30,'12:20-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',500000,2),(25,3,3,'Phụng Công, Văn Giang, Hưng Yên','20.955500','105.925589',5,10,'12:20-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',2500000,2),(26,4,3,'TT. Văn Giang, Văn Giang, Hưng Yên','20.946659','105.941997',4,15,'12:20-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',250000,2),(27,5,3,'Văn Giang, Hưng Yên','20.965983','105.974496',25,30,'08:20-23:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',240000,2),(28,6,3,'Tân Tiến Văn Giang, Hưng Yên','20.926672','105.963563',10,37,'08:20-23:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',1000000,2),(29,7,3,'Đào Dương An Khải, Hưng Yên, Việt Nam','20.845913','106.091003',25,25,'10:20-14:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',250000,2),(30,8,3,'Đa Lộc Ân Thi, Hưng Yên, Việt Nam','20.774430','106.134831',1,25,'08:20-23:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',100000,2),(31,9,3,'P. Hiền Nam Hưng Yên','20.656216','106.056927',1,34,'07:20-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',99000,2),(41,1,5,'Vân Hồ Mộc Châu Sơn La','20.782506','104.802344',25,25,'07:20-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(42,3,1,'Phú Đô Nam Từ Liêm Hà Nội','21.011625','105.766160',0,30,'07:20-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(43,4,1,'Mỹ Đình Nam Từ Liêm Hà Nội','21.021370','105.765918',0,20,'05:30-23:20h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(44,5,1,'Gần SVĐ Mỹ Đình Nam Từ Liêm Hà Nội','21.020608','105.766938',0,30,'06:40-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(45,6,1,'Rạp chiếu phim Beta Nam Từ Liêm Hà Nội','21.005076','105.776124',0,87,'05:00-23:30h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(46,7,1,'Bến Xe Mỹ Đình Hà Nội','21.016509','105.782555',0,50,'07:25-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(47,8,1,'Mễ Trì Thương Nam Từ Liêm Hà Nội','21.004028','105.780206',0,30,'07:25-21:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3),(48,9,1,'Khu đô thị E Co','21.006506','105.749791',0,40,'12:20-22:00h','http://dantri.vcmedia.vn/Uploaded/2011/03/07/61dbaidoxe1.jpg',0,3);
/*!40000 ALTER TABLE `parking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parking_id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parking_staff_idFK_idx` (`parking_id`),
  CONSTRAINT `parking_staff_idFK` FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,1,'Đinh Duy Mạnh','0968949064','fe4d36fb3827d314b6e48d2ffc601833','Tây Tựu-Từ Liêm-Hà Nội'),(2,2,'Âu Văn Thịnh','0967239959','fe4d36fb3827d314b6e48d2ffc601833','Việt Hưng-Long Biên-Hà Nội'),(3,3,'Bùi Quang Hưng','01665195880','0a6c1a85c675f6817e725e4f3a41ef1f','33 Nguyễn Khánh Toàn-Quan Hoa-Cầu Giấy-Hà Nội'),(4,1,'Nguyễn Đăng Khôi','01665195808','0a6c1a85c675f6817e725e4f3a41ef1f','Long Biên-Hà Nội');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parking_id` int(11) NOT NULL,
  `vehicletype_id` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tariff_parking_idFK_idx` (`parking_id`),
  KEY `tariff_vehicle_idFK_idx` (`vehicletype_id`),
  CONSTRAINT `tariff_parking_idFK` FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tariff_vehicle_idFK` FOREIGN KEY (`vehicletype_id`) REFERENCES `vehicletype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES (1,1,1,50000),(2,1,2,55000),(3,1,3,60000),(55,2,1,50000),(56,2,2,55000),(57,2,3,60000),(58,3,1,50000),(59,3,2,55000),(60,3,3,60000),(61,4,1,50000),(62,4,2,55000),(63,4,3,60000),(64,5,1,50000),(65,5,2,55000),(66,5,3,60000),(67,6,1,50000),(68,6,2,55000),(69,6,3,60000),(70,7,1,50000),(71,7,2,55000),(72,7,3,60000),(73,8,1,50000),(74,8,2,55000),(75,8,3,60000),(76,9,1,50000),(77,9,2,55000),(78,9,3,60000),(79,17,1,50000),(80,17,2,55000),(81,17,3,60000),(82,18,1,50000),(83,18,2,55000),(84,18,3,60000),(85,19,1,50000),(86,19,2,55000),(87,19,3,60000),(88,20,1,50000),(89,20,2,55000),(90,20,3,60000),(91,21,1,50000),(92,21,2,55000),(93,21,3,60000),(94,22,1,50000),(95,22,2,55000),(96,22,3,60000),(97,23,1,50000),(98,23,2,55000),(99,23,3,60000),(100,24,1,50000),(101,24,2,55000),(102,24,3,60000),(103,25,1,50000),(104,25,2,55000),(105,25,3,60000),(106,26,1,50000),(107,26,2,55000),(108,26,3,60000),(109,27,1,50000),(110,27,2,55000),(111,27,3,60000),(112,28,1,50000),(113,28,2,60000),(114,28,3,60000),(115,29,1,50000),(116,29,2,55000),(117,29,3,60000),(118,30,1,50000),(119,30,2,55000),(120,30,3,60000),(121,31,1,50000),(122,31,2,55000),(123,31,3,60000),(124,41,1,50000),(125,41,2,55000),(126,41,3,60000);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicletype_id` int(11) NOT NULL,
  `licenseplate` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `color` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `vehicle_type_idFK_idx` (`vehicletype_id`),
  CONSTRAINT `vehicle_type_idFK` FOREIGN KEY (`vehicletype_id`) REFERENCES `vehicletype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (1,1,'30C 01585','Trắng',1),(2,1,'34H1 37585','Xanh Ngọc',1),(3,3,'22H1 1234','Nâu',1),(4,2,'27H 4567','Đen',1),(5,1,'33L6 3318','Ghi',1),(15,1,'30C2 01585','Đỏ',1),(17,1,'30C6 01585','Ghi',1),(18,3,'34H1 2596','Đen',1),(19,1,'36B1 7526','Đỏ',1),(20,2,'34X8 0512','Đen',0),(21,2,'21A8 8888','Xanh',0),(22,3,'27B2 9695','Đen',0),(23,2,'30Z1 86868','Đen',0),(24,1,'30Z1 13865','Cam',0),(25,2,'54A 5086','Trắng',0),(26,1,'29A1 9999','đen',0),(27,2,'30H 23211','Trắng',1),(28,2,'30T2 4444','Trắng',1);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicletype`
--

DROP TABLE IF EXISTS `vehicletype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicletype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicletype`
--

LOCK TABLES `vehicletype` WRITE;
/*!40000 ALTER TABLE `vehicletype` DISABLE KEYS */;
INSERT INTO `vehicletype` VALUES (1,'4-7 chỗ'),(2,'9-16 chỗ'),(3,'30-45 chỗ');
/*!40000 ALTER TABLE `vehicletype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-29 12:16:18
