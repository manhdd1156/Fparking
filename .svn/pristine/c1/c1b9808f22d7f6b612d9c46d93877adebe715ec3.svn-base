-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2018 at 07:53 PM
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
  `parkingID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driverID` int(11) NOT NULL,
  `phoneNumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng của lái xe';

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
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `peopleID` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `imgID` int(11) DEFAULT NULL,
  `deposits` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parkinginfor`
--

CREATE TABLE `parkinginfor` (
  `parkingID` int(11) NOT NULL,
  `phonenumber` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `adress` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `longitude` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `latitude` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `space` int(11) NOT NULL,
  `parkingPriceID` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `parkingLotID` int(11) NOT NULL,
  `reportID` int(11) NOT NULL,
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flag_del` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parkinglot`
--

CREATE TABLE `parkinglot` (
  `parkingLotID` int(11) NOT NULL,
  `spaceNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `typecar`
--

CREATE TABLE `typecar` (
  `typeCarID` int(11) NOT NULL,
  `type` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  ADD KEY `ownerID` (`ownerID`),
  ADD KEY `parkingLotID` (`parkingLotID`);

--
-- Indexes for table `parkinglot`
--
ALTER TABLE `parkinglot`
  ADD PRIMARY KEY (`parkingLotID`);

--
-- Indexes for table `parkingprice`
--
ALTER TABLE `parkingprice`
  ADD PRIMARY KEY (`parkingPriceID`),
  ADD KEY `parkingID` (`parkingID`),
  ADD KEY `typeCarID` (`typeCarID`);

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
  MODIFY `bookingID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `driverID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `imageID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ownerinfor`
--
ALTER TABLE `ownerinfor`
  MODIFY `ownerID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parkinginfor`
--
ALTER TABLE `parkinginfor`
  MODIFY `parkingID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parkinglot`
--
ALTER TABLE `parkinglot`
  MODIFY `parkingLotID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parkingprice`
--
ALTER TABLE `parkingprice`
  MODIFY `parkingPriceID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `typecar`
--
ALTER TABLE `typecar`
  MODIFY `typeCarID` int(11) NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `FK_ParkingInfor_OwnerInfor` FOREIGN KEY (`ownerID`) REFERENCES `ownerinfor` (`ownerID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ParkingInfor_ParkingLot` FOREIGN KEY (`parkingLotID`) REFERENCES `parkinglot` (`parkingLotID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `parkingprice`
--
ALTER TABLE `parkingprice`
  ADD CONSTRAINT `FK_ParkingPrice_ParkingInfor` FOREIGN KEY (`parkingID`) REFERENCES `parkinginfor` (`parkingID`) ON DELETE NO ACTION ON UPDATE CASCADE,
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
