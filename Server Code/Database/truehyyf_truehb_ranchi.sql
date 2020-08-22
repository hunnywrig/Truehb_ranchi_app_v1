-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 22, 2020 at 06:45 AM
-- Server version: 5.6.41-84.1
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `truehyyf_truehb_ranchi`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_table`
--

CREATE TABLE `admin_table` (
  `a_id` int(11) NOT NULL,
  `s_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `ph_no` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `acive_status` tinyint(4) NOT NULL DEFAULT '1',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin_table`
--

INSERT INTO `admin_table` (`a_id`, `s_id`, `name`, `email`, `ph_no`, `password`, `time_stamp`, `acive_status`, `last_login`) VALUES
(1, 1, 'block1_admin ', 'block1.admin@gmail.com', '0123456789', '9e9c8a0db58d052a24928928c10c8dc1', '2020-07-04 08:21:46', 1, '2020-07-04 08:21:46');

-- --------------------------------------------------------

--
-- Table structure for table `super_admin_table`
--

CREATE TABLE `super_admin_table` (
  `s_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `ph_no` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `acive_status` tinyint(4) NOT NULL DEFAULT '1',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `super_admin_table`
--

INSERT INTO `super_admin_table` (`s_id`, `name`, `email`, `ph_no`, `password`, `time_stamp`, `acive_status`, `last_login`) VALUES
(1, 'super_admin', 'super.admin@gmail.com', '0123456789', 'e4b3829bca6c7c99ce58f9e3f100c36f', '2020-07-04 08:17:31', 1, '2020-07-10 07:04:37');

-- --------------------------------------------------------

--
-- Table structure for table `test_details_table`
--

CREATE TABLE `test_details_table` (
  `id` int(11) NOT NULL,
  `test_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `u_id` int(11) NOT NULL,
  `client_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `client_age` tinyint(4) NOT NULL,
  `client_gender` tinyint(4) NOT NULL,
  `client_pregnant_status` tinyint(4) NOT NULL,
  `client_hb_value` float NOT NULL,
  `district` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `block` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `phc_uhc_sc` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `server_status` tinyint(4) NOT NULL DEFAULT '1',
  `test_time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `insertt_time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `test_details_table`
--

INSERT INTO `test_details_table` (`id`, `test_id`, `u_id`, `client_name`, `client_age`, `client_gender`, `client_pregnant_status`, `client_hb_value`, `district`, `block`, `phc_uhc_sc`, `server_status`, `test_time_stamp`, `insertt_time_stamp`) VALUES
(7, 'c239bb52-d3a6-4_1594135650389', 1, 'hhhh', 66, 2, 0, 13.2, 'Ranchi', 'Block 3', 'Center 2', 1, '2020-07-07 20:57:30', '2020-07-07 15:27:52'),
(8, '997ceb80-90a6-4_1594136160943', 1, 'jjhh', 66, 2, 1, 22.2, 'Ranchi', 'Block 3', 'Center 3', 1, '2020-07-07 21:06:00', '2020-07-07 15:43:01'),
(9, '8f3f9666-f6d0-4_1594136565474', 1, 'raj', 12, 1, 0, 22.2, 'Ranchi', 'Block 1', 'Center 2', 1, '2020-07-07 21:12:45', '2020-07-07 15:43:01');

-- --------------------------------------------------------

--
-- Table structure for table `user_table`
--

CREATE TABLE `user_table` (
  `u_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `ph_no` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `acive_status` tinyint(4) NOT NULL DEFAULT '1',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_table`
--

INSERT INTO `user_table` (`u_id`, `a_id`, `name`, `email`, `ph_no`, `password`, `time_stamp`, `acive_status`, `last_login`) VALUES
(1, 1, 'raj', 'raj.user@gmail.com', '0123456789', 'c13b1fd8824c5577074601c325503a9a', '2020-07-04 08:23:14', 1, '2020-07-10 06:50:07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_table`
--
ALTER TABLE `admin_table`
  ADD PRIMARY KEY (`a_id`),
  ADD KEY `foreignkey` (`s_id`);

--
-- Indexes for table `super_admin_table`
--
ALTER TABLE `super_admin_table`
  ADD PRIMARY KEY (`s_id`);

--
-- Indexes for table `test_details_table`
--
ALTER TABLE `test_details_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `foreignkey_test_details` (`u_id`);

--
-- Indexes for table `user_table`
--
ALTER TABLE `user_table`
  ADD PRIMARY KEY (`u_id`),
  ADD KEY `foreignkey_user` (`a_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_table`
--
ALTER TABLE `admin_table`
  MODIFY `a_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `super_admin_table`
--
ALTER TABLE `super_admin_table`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `test_details_table`
--
ALTER TABLE `test_details_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user_table`
--
ALTER TABLE `user_table`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin_table`
--
ALTER TABLE `admin_table`
  ADD CONSTRAINT `foreignkey` FOREIGN KEY (`s_id`) REFERENCES `super_admin_table` (`s_id`);

--
-- Constraints for table `test_details_table`
--
ALTER TABLE `test_details_table`
  ADD CONSTRAINT `foreignkey_test_details` FOREIGN KEY (`u_id`) REFERENCES `user_table` (`u_id`);

--
-- Constraints for table `user_table`
--
ALTER TABLE `user_table`
  ADD CONSTRAINT `foreignkey_user` FOREIGN KEY (`a_id`) REFERENCES `admin_table` (`a_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
