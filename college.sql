-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2022 at 05:46 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `college`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignment_info`
--

CREATE TABLE `assignment_info` (
  `assi_id` int(40) NOT NULL,
  `assi_title` varchar(40) NOT NULL,
  `assi_date` varchar(40) NOT NULL,
  `assi_type` varchar(40) NOT NULL,
  `assi_dep` varchar(40) NOT NULL,
  `assi_faculty` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `assignment_info`
--

INSERT INTO `assignment_info` (`assi_id`, `assi_title`, `assi_date`, `assi_type`, `assi_dep`, `assi_faculty`) VALUES
(1, 'Assignment N', '26-12-2021', 'Here show assignment on etc.', 'MCA', 'Teacher Name'),
(2, 'Assignment on ABC', '10-1-2022', 'Here show assignment on etc.', 'MCA', 'Teacher Name');

-- --------------------------------------------------------

--
-- Table structure for table `college_contact`
--

CREATE TABLE `college_contact` (
  `col_name` varchar(40) NOT NULL,
  `col_contact` varchar(40) NOT NULL,
  `col_link` varchar(100) NOT NULL,
  `col_email` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `college_contact`
--

INSERT INTO `college_contact` (`col_name`, `col_contact`, `col_link`, `col_email`) VALUES
('Name', '+91 1234567890', '', 'example@domain.com'),
('Name', '+91 1234567890', '', 'example@domain.com'),
('Website', '', 'https://www.example.com', '');

-- --------------------------------------------------------

--
-- Table structure for table `college_events`
--

CREATE TABLE `college_events` (
  `n_id` int(10) NOT NULL,
  `n_date` varchar(40) NOT NULL,
  `n_title` varchar(40) NOT NULL,
  `n_message` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `college_events`
--

INSERT INTO `college_events` (`n_id`, `n_date`, `n_title`, `n_message`) VALUES
(1, '../../....', 'News Title', 'Here show assignment on etc.'),
(2, '2/12/2021', 'Notice', 'Here show notice.'),
(3, '22/12/2021', 'Events', 'Here show notice.');

-- --------------------------------------------------------

--
-- Table structure for table `college_gallery`
--

CREATE TABLE `college_gallery` (
  `id` int(10) NOT NULL,
  `type` varchar(40) NOT NULL,
  `path` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `student_account`
--

CREATE TABLE `student_account` (
  `std_id` varchar(40) NOT NULL,
  `std_password` varchar(50) NOT NULL,
  `std_name` varchar(50) NOT NULL,
  `std_image` varchar(10) NOT NULL,
  `std_department` varchar(40) NOT NULL,
  `std_status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_account`
--

INSERT INTO `student_account` (`std_id`, `std_password`, `std_name`, `std_image`, `std_department`, `std_status`) VALUES
('1001', '123456', 'Himanshu Srivastava', '1001.jpg', 'MCA', 2),
('1002', '123456', 'Saurabh Srivastava', '1002.jpg', 'MCA', 2),
('1003', '123456', 'Aayushi Tyagi', '1003.jpg', 'MCA', 3),
('1004', '123456', 'Ritik Raj', '1004.jpg', 'MCA', 1),
('1005', '123456', 'Mayank Srivastava', '1005.jpg', 'MCA', 1),
('1006', '123456', 'Jeevash', '1006.jpg', 'MCA', 3),
('1007', '123456', 'Manish Mishra', '1007.jpg', 'MCA', 2);

-- --------------------------------------------------------

--
-- Table structure for table `student_bio`
--

CREATE TABLE `student_bio` (
  `std_id` varchar(40) NOT NULL,
  `std_father` varchar(45) NOT NULL,
  `std_mother` varchar(45) NOT NULL,
  `std_dob` varchar(45) NOT NULL,
  `std_city` varchar(45) NOT NULL,
  `std_dist` varchar(45) NOT NULL,
  `std_pin` varchar(45) NOT NULL,
  `std_sem` varchar(45) NOT NULL,
  `std_contact` varchar(45) NOT NULL,
  `std_accadmic` varchar(45) NOT NULL,
  `std_email` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_bio`
--

INSERT INTO `student_bio` (`std_id`, `std_father`, `std_mother`, `std_dob`, `std_city`, `std_dist`, `std_pin`, `std_sem`, `std_contact`, `std_accadmic`, `std_email`) VALUES
('1001', 'Anil Kumar Srivastava', 'Kiran Srivastava', '10-06-1999', 'Tarabganj', 'Gonda', '271403', '3', '7081654255', '2020-2022', 'aasoogames@gmail.com'),
('1007', 'SANTOSH KUMAR MISHRA', 'RITA MISHRA', '29-02-1998', 'VARANASI', 'VARANASI', '221103', '3', '1234567890', '2020-2022', 'manishmishra@domain.name');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignment_info`
--
ALTER TABLE `assignment_info`
  ADD PRIMARY KEY (`assi_id`);

--
-- Indexes for table `college_events`
--
ALTER TABLE `college_events`
  ADD PRIMARY KEY (`n_id`);

--
-- Indexes for table `college_gallery`
--
ALTER TABLE `college_gallery`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_account`
--
ALTER TABLE `student_account`
  ADD PRIMARY KEY (`std_id`);

--
-- Indexes for table `student_bio`
--
ALTER TABLE `student_bio`
  ADD PRIMARY KEY (`std_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignment_info`
--
ALTER TABLE `assignment_info`
  MODIFY `assi_id` int(40) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `college_events`
--
ALTER TABLE `college_events`
  MODIFY `n_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `college_gallery`
--
ALTER TABLE `college_gallery`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
