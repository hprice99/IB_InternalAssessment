-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 31, 2016 at 09:46 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


create database woods;
use woods;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `woods`
--

-- --------------------------------------------------------

--
-- Table structure for table `customerfixtures`
--

CREATE TABLE `customerfixtures` (
  `customerIdFK` smallint(5) UNSIGNED NOT NULL,
  `fixtureIdFK` smallint(5) UNSIGNED NOT NULL,
  `paymentMade` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerfixtures`
--

INSERT INTO `customerfixtures` (`customerIdFK`, `fixtureIdFK`, `paymentMade`) VALUES
(2, 42, 0),
(3, 42, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customerlessons`
--

CREATE TABLE `customerlessons` (
  `customerIdFK` smallint(5) UNSIGNED NOT NULL,
  `lessonIdFK` smallint(5) UNSIGNED NOT NULL,
  `paymentMade` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerlessons`
--

INSERT INTO `customerlessons` (`customerIdFK`, `lessonIdFK`, `paymentMade`) VALUES
(2, 41, 0),
(3, 41, 0),
(3, 43, 0),
(4, 43, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customerId` smallint(5) UNSIGNED NOT NULL,
  `fName` varchar(50) NOT NULL,
  `lName` varchar(50) NOT NULL,
  `addressLine1` varchar(50) NOT NULL,
  `addressLine2` varchar(50) DEFAULT NULL,
  `suburb` varchar(30) NOT NULL,
  `postCode` char(4) NOT NULL,
  `state` enum('QLD','NSW','ACT','VIC','TAS','SA','WA','NT') NOT NULL,
  `email` varchar(75) NOT NULL,
  `phone` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customerId`, `fName`, `lName`, `addressLine1`, `addressLine2`, `suburb`, `postCode`, `state`, `email`, `phone`) VALUES
(2, 'Ben', 'Jones', '15 Wimpole Street', '', 'Toowong', '4066', 'QLD', 'ibcomputersciencetestemail@gmail.com ', '0732456787'),
(3, 'Rod', 'Jones', '14 Johnson Street', '', 'Brisbane', '4000', 'QLD', 'ibcomputersciencetestemail@gmail.com', '0400123456'),
(4, 'Ben', 'Higgins', '18 Wimpole Street', '', 'Brisbane', '4000', 'QLD', 'ibcomputersciencetestemail@gmail.com ', '0400123456');

-- --------------------------------------------------------

--
-- Table structure for table `fixtures`
--

CREATE TABLE `fixtures` (
  `productIdFK` smallint(5) UNSIGNED NOT NULL,
  `time` time NOT NULL,
  `duration` smallint(3) UNSIGNED NOT NULL,
  `day` enum('monday','tuesday','wednesday','thursday','friday','saturday','sunday') NOT NULL,
  `term` enum('1','2','3','4','Holiday') NOT NULL,
  `year` year(4) NOT NULL,
  `location` enum('AH','AC','EH','AE','SD') NOT NULL,
  `datePaymentDue` date NOT NULL,
  `positions` tinyint(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fixtures`
--

INSERT INTO `fixtures` (`productIdFK`, `time`, `duration`, `day`, `term`, `year`, `location`, `datePaymentDue`, `positions`) VALUES
(42, '15:30:00', 120, 'friday', '3', 2016, 'AH', '2016-06-27', 15),
(46, '09:30:00', 120, 'saturday', '2', 2016, 'AH', '2016-06-28', 15),
(48, '12:00:00', 120, 'sunday', '2', 2016, 'AH', '2016-06-21', 15);

-- --------------------------------------------------------

--
-- Table structure for table `grouplessons`
--

CREATE TABLE `grouplessons` (
  `productIdFK` smallint(5) UNSIGNED NOT NULL,
  `staffIdFK` smallint(5) UNSIGNED NOT NULL,
  `datePaymentDue` date NOT NULL,
  `location` enum('AH','AC','AS','SD','AE') NOT NULL,
  `time` time NOT NULL,
  `duration` smallint(3) UNSIGNED DEFAULT NULL,
  `day` enum('monday','tuesday','wednesday','thursday','friday','saturday','sunday') NOT NULL,
  `term` enum('1','1.5','2','2.5','3','3.5','4','4.5') NOT NULL,
  `year` year(4) NOT NULL,
  `positions` tinyint(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grouplessons`
--

INSERT INTO `grouplessons` (`productIdFK`, `staffIdFK`, `datePaymentDue`, `location`, `time`, `duration`, `day`, `term`, `year`, `positions`) VALUES
(41, 16, '2016-06-09', 'AH', '15:30:00', 30, 'friday', '2', 2016, 4),
(43, 16, '2016-06-28', 'AH', '08:30:00', 60, 'saturday', '3', 2016, 15),
(44, 17, '2016-06-24', 'AH', '09:00:00', 30, 'saturday', '2', 2016, 4),
(47, 16, '2016-06-22', 'AH', '15:30:00', 30, 'saturday', '2', 2016, 12),
(49, 17, '2016-07-28', 'AH', '15:00:00', 15, 'tuesday', '3', 2016, 15);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `productId` smallint(5) UNSIGNED NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` enum('Equipment','Food/Drink','Lesson','Fixture','Clothing') NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` smallint(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productId`, `name`, `category`, `price`, `quantity`) VALUES
(35, 'Snickers', 'Food/Drink', '1.80', 18),
(36, 'Coca-cola (600mL)', 'Food/Drink', '2.40', 0),
(37, 'Woods Shirt M', 'Clothing', '20.00', 2),
(41, 'Friday 3:30pm', 'Lesson', '190.00', 4),
(42, 'Friday afternoon', 'Fixture', '210.00', 15),
(43, 'Saturday 8:30am', 'Lesson', '120.00', 15),
(44, 'Saturday 9:00am', 'Lesson', '210.00', 4),
(45, 'Sprite (600mL)', 'Food/Drink', '3.20', 11),
(46, 'Saturday 9:30am', 'Fixture', '210.00', 15),
(47, 'Saturday 3:30pm', 'Lesson', '120.00', 12),
(48, 'Sunday 12:00pm', 'Fixture', '130.00', 15),
(49, 'Tuesday 3:00pm', 'Lesson', '200.00', 15);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffId` smallint(5) UNSIGNED NOT NULL,
  `username` varchar(50) NOT NULL,
  `fName` varchar(50) NOT NULL,
  `lName` varchar(50) NOT NULL,
  `password` varchar(8000) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staffId`, `username`, `fName`, `lName`, `password`, `admin`) VALUES
(16, 'rod', 'Rod', 'Woods', '[-107, 36, -21, 12, -76, 96, -54, -15, 97, -64, 0, -35, 72, 72, 60, -78, 6, 7, 92, -42]', 1),
(17, 'brett', 'Brett', 'Smith', '[30, 27, -127, 54, -43, -98, -113, 120, 39, 34, -4, -34, 57, -95, 26, -89, -121, -4, -18, 38]', 1);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transactionId` int(11) UNSIGNED NOT NULL,
  `customerIdFK` smallint(5) UNSIGNED NOT NULL,
  `staffIdFK` smallint(5) UNSIGNED NOT NULL,
  `productIdFK` smallint(5) UNSIGNED NOT NULL,
  `paymentMethod` enum('CASH','CARD','CHEQ','') NOT NULL,
  `time` datetime NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `quantity` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transactionId`, `customerIdFK`, `staffIdFK`, `productIdFK`, `paymentMethod`, `time`, `cost`, `quantity`) VALUES
(1, 1, 16, 35, 'CASH', '2016-06-05 00:00:00', '1.80', 1),
(2, 1, 16, 35, 'CASH', '2016-06-05 18:50:30', '1.80', 1),
(5, 1, 16, 45, 'CASH', '2016-06-11 20:56:51', '9.60', 3),
(6, 1, 16, 35, 'CARD', '2016-06-11 21:00:39', '5.40', 3),
(7, 1, 16, 45, 'CARD', '2016-06-11 21:25:31', '3.20', 1),
(8, 1, 16, 45, 'CARD', '2016-06-11 23:10:19', '6.40', 2),
(9, 1, 16, 45, 'CARD', '2016-06-11 23:39:01', '9.60', 3),
(10, 2, 16, 35, 'CASH', '2016-06-27 16:43:31', '1.80', 1),
(11, 0, 16, 35, 'CASH', '2016-06-27 16:45:25', '1.80', 1),
(12, 2, 16, 36, 'CASH', '2016-06-27 16:50:30', '2.40', 1),
(13, 2, 16, 35, 'CASH', '2016-06-27 16:51:30', '1.80', 1),
(14, 2, 16, 35, 'CASH', '2016-06-27 16:56:44', '1.80', 1),
(15, 3, 16, 36, 'CASH', '2016-06-27 22:10:49', '2.40', 1),
(16, 0, 16, 35, 'CASH', '2016-07-12 12:22:58', '1.80', 1),
(17, 0, 16, 35, 'CASH', '2016-07-12 12:22:58', '1.80', 1),
(18, 3, 16, 36, 'CASH', '2016-07-12 12:32:35', '28.80', 12),
(19, 3, 16, 35, 'CHEQ', '2016-07-17 00:03:34', '3.60', 2),
(20, 3, 16, 36, 'CASH', '2016-07-17 00:12:10', '7.20', 3),
(21, 3, 16, 36, 'CASH', '2016-07-23 22:26:02', '7.20', 3),
(22, 3, 16, 36, 'CASH', '2016-07-23 23:15:40', '9.60', 4),
(23, 2, 16, 36, 'CASH', '2016-07-23 23:32:40', '4.80', 2),
(24, 2, 16, 36, 'CASH', '2016-07-23 23:42:29', '7.20', 3),
(25, 2, 16, 35, 'CASH', '2016-07-29 23:09:46', '1.80', 1),
(26, 2, 16, 36, 'CASH', '2016-07-29 23:32:00', '4.80', 2),
(27, 2, 16, 36, 'CASH', '2016-07-30 09:45:33', '2.40', 1),
(28, 2, 16, 36, 'CASH', '2016-07-30 09:55:29', '2.40', 1),
(29, 2, 16, 36, 'CASH', '2016-07-30 16:11:19', '2.40', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customerfixtures`
--
ALTER TABLE `customerfixtures`
  ADD PRIMARY KEY (`customerIdFK`,`fixtureIdFK`);

--
-- Indexes for table `customerlessons`
--
ALTER TABLE `customerlessons`
  ADD PRIMARY KEY (`customerIdFK`,`lessonIdFK`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerId`);

--
-- Indexes for table `fixtures`
--
ALTER TABLE `fixtures`
  ADD PRIMARY KEY (`productIdFK`);

--
-- Indexes for table `grouplessons`
--
ALTER TABLE `grouplessons`
  ADD PRIMARY KEY (`productIdFK`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`productId`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffId`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionId`),
  ADD UNIQUE KEY `transactionId` (`transactionId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customerId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `grouplessons`
--
ALTER TABLE `grouplessons`
  MODIFY `productIdFK` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `productId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staffId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
