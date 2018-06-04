-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 03, 2018 at 05:53 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fparking`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookinginfor`
--

CREATE TABLE `bookinginfor` (
  `bookingID` int(11) NOT NULL,
  `carID` int(11) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `checkinTime` date DEFAULT NULL,
  `checkoutTime` date DEFAULT NULL,
  `status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `bookinginfor`
--

INSERT INTO `bookinginfor` (`bookingID`, `carID`, `parkingID`, `checkinTime`, `checkoutTime`, `status`) VALUES
(3, 1, 3, NULL, NULL, 1),
(4, 2, 4, NULL, NULL, 2),
(5, 3, 3, NULL, NULL, 3),
(14, 2, 3, NULL, NULL, 1),
(15, 3, 3, NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `carofdriver`
--

CREATE TABLE `carofdriver` (
  `carID` int(11) NOT NULL,
  `driverID` int(11) NOT NULL,
  `typeCarID` int(11) NOT NULL,
  `licensePlate` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `carofdriver`
--

INSERT INTO `carofdriver` (`carID`, `driverID`, `typeCarID`, `licensePlate`) VALUES
(1, 1, 1, '33L6-3318'),
(2, 2, 2, '34X2-32242'),
(3, 3, 1, '13L6-6546');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driverID` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phoneNumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `status` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng của lái xe';

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driverID`, `name`, `phoneNumber`, `password`, `date`, `status`) VALUES
(1, 'Đinh Duy Mạnh', '096885456', '123456', '1996-05-06', NULL),
(2, 'Đinh Duy A', '0968821012', '123456', '1996-05-15', NULL),
(3, 'Đinh Duy B', '0968821545', '123456', '1998-05-19', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `imageID` int(11) NOT NULL,
  `parkingID` int(11) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ownerinfor`
--

CREATE TABLE `ownerinfor` (
  `ownerID` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phoneNumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `peopleID` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `imgID` int(11) DEFAULT NULL,
  `deposits` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ownerinfor`
--

INSERT INTO `ownerinfor` (`ownerID`, `name`, `phoneNumber`, `password`, `email`, `peopleID`, `imgID`, `deposits`) VALUES
(1, 'nguyen van A', '0968949064', '123456', NULL, '9958593282', NULL, 0),
(2, 'Nguyễn Văn B', '0968949065', '123456', 'manhholong2@gmail.com', '15165196165', NULL, 1000000);

-- --------------------------------------------------------

--
-- Table structure for table `parkinginfor`
--

CREATE TABLE `parkinginfor` (
  `parkingID` int(11) NOT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phoneNumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `longitude` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `latitude` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `space` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `status` int(10) DEFAULT NULL,
  `flag_del` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `parkinginfor`
--

INSERT INTO `parkinginfor` (`parkingID`, `address`, `phoneNumber`, `longitude`, `latitude`, `space`, `ownerID`, `status`, `flag_del`) VALUES
(3, 'Khâm Thiên - Hà Nội', '0968949065', '48441165', '15616541654', 30, 1, NULL, 0),
(4, 'Nhà thờ - Hà Nội', '0968949066', '4894132', '1654984', 25, 2, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `parkingprice`
--

CREATE TABLE `parkingprice` (
  `parkingPriceID` int(11) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `typeCarID` int(11) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `parkingprice`
--

INSERT INTO `parkingprice` (`parkingPriceID`, `parkingID`, `typeCarID`, `price`) VALUES
(1, 3, 1, 50000),
(2, 3, 2, 60000),
(3, 4, 1, 20000);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportID` int(11) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `bookingID` int(11) NOT NULL,
  `content` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`reportID`, `parkingID`, `bookingID`, `content`) VALUES
(1, 3, 3, 'Hơi khó chịu');

-- --------------------------------------------------------

--
-- Table structure for table `typecar`
--

CREATE TABLE `typecar` (
  `typeCarID` int(11) NOT NULL,
  `type` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `typecar`
--

INSERT INTO `typecar` (`typeCarID`, `type`) VALUES
(1, '4 chỗ'),
(2, '7-9 chỗ'),
(3, '16 chỗ'),
(4, '30 chỗ'),
(5, '45 chỗ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookinginfor`
--
ALTER TABLE `bookinginfor`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `carID` (`carID`),
  ADD KEY `parkingID` (`parkingID`) USING BTREE;

--
-- Indexes for table `carofdriver`
--
ALTER TABLE `carofdriver`
  ADD PRIMARY KEY (`carID`),
  ADD UNIQUE KEY `licensePlate` (`licensePlate`),
  ADD KEY `typeCarID` (`typeCarID`),
  ADD KEY `driverID` (`driverID`) USING BTREE;

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driverID`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`imageID`),
  ADD KEY `parkingID` (`parkingID`);

--
-- Indexes for table `ownerinfor`
--
ALTER TABLE `ownerinfor`
  ADD PRIMARY KEY (`ownerID`),
  ADD KEY `imgID` (`imgID`);

--
-- Indexes for table `parkinginfor`
--
ALTER TABLE `parkinginfor`
  ADD PRIMARY KEY (`parkingID`),
  ADD KEY `ownerID` (`ownerID`);

--
-- Indexes for table `parkingprice`
--
ALTER TABLE `parkingprice`
  ADD PRIMARY KEY (`parkingPriceID`),
  ADD KEY `typeCarID` (`typeCarID`),
  ADD KEY `FK_ParkingPrice_ParkingInfor` (`parkingID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportID`),
  ADD KEY `bookingID` (`bookingID`),
  ADD KEY `parkingID` (`parkingID`);

--
-- Indexes for table `typecar`
--
ALTER TABLE `typecar`
  ADD PRIMARY KEY (`typeCarID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookinginfor`
--
ALTER TABLE `bookinginfor`
  MODIFY `bookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `carofdriver`
--
ALTER TABLE `carofdriver`
  MODIFY `carID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `driverID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `imageID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ownerinfor`
--
ALTER TABLE `ownerinfor`
  MODIFY `ownerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `parkinginfor`
--
ALTER TABLE `parkinginfor`
  MODIFY `parkingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `parkingprice`
--
ALTER TABLE `parkingprice`
  MODIFY `parkingPriceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `typecar`
--
ALTER TABLE `typecar`
  MODIFY `typeCarID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookinginfor`
--
ALTER TABLE `bookinginfor`
  ADD CONSTRAINT `FK_BookingInfor_CarOfDriver` FOREIGN KEY (`carID`) REFERENCES `carofdriver` (`carID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_BookingInfor_ParkingInfor` FOREIGN KEY (`parkingID`) REFERENCES `parkinginfor` (`parkingID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `carofdriver`
--
ALTER TABLE `carofdriver`
  ADD CONSTRAINT `FK_CarOfDriver_Driver` FOREIGN KEY (`driverID`) REFERENCES `driver` (`driverID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_CarOfDriver_TypeCar` FOREIGN KEY (`typeCarID`) REFERENCES `typecar` (`typeCarID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `FK_Image_ParkingInfor` FOREIGN KEY (`parkingID`) REFERENCES `parkinginfor` (`parkingID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `ownerinfor`
--
ALTER TABLE `ownerinfor`
  ADD CONSTRAINT `FK_OwnerInfor_Image` FOREIGN KEY (`imgID`) REFERENCES `image` (`imageID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `parkinginfor`
--
ALTER TABLE `parkinginfor`
  ADD CONSTRAINT `FK_ParkingInfor_OwnerInfor` FOREIGN KEY (`ownerID`) REFERENCES `ownerinfor` (`ownerID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `parkingprice`
--
ALTER TABLE `parkingprice`
  ADD CONSTRAINT `FK_ParkingPrice_ParkingInfor` FOREIGN KEY (`parkingID`) REFERENCES `parkinginfor` (`parkingID`),
  ADD CONSTRAINT `FK_ParkingPrice_TypeCar` FOREIGN KEY (`typeCarID`) REFERENCES `typecar` (`typeCarID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `FK_Report_BookingInfor` FOREIGN KEY (`bookingID`) REFERENCES `bookinginfor` (`bookingID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Report_ParkingInfor` FOREIGN KEY (`parkingID`) REFERENCES `parkinginfor` (`parkingID`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
