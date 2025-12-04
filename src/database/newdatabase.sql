CREATE DATABASE  IF NOT EXISTS `video_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `video_store`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: video_store
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `movie_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `director` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `writers` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `running_time_minutes` smallint DEFAULT NULL,
  `rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `genre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cast` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `poster_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `synopsis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `available_dvd` tinyint NOT NULL DEFAULT '1',
  `available_bluray` tinyint NOT NULL DEFAULT '1',
  `costo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'Incep','Christopher Nolan','Christopher Nolan','2010-07-12',148,'PG-13','Sci-Fi, Action','Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy','https://m.media-amazon.com/images/I/71SBgi0X2KL._AC_UF894,1000_QL80_.jpg','A skilled thief enters people\'s dreams to steal secrets, but a final mission forces him to plant an idea instead of taking one.','2025-12-01 16:57:55',1,0,5.50),(2,'Black Panther','Ryan Coogler','Ryan Coogler; Joe Robert Cole','2018-02-16',134,'PG-13','Action; Adventure; Superhero','Chadwick Boseman; Michael B. Jordan; Lupita Nyong\'o; Danai Gurira; Letitia Wright; Angela Bassett','https://i.ebayimg.com/images/g/QJUAAOSw03Ji5WFE/s-l1600.jpg','T’Challa returns to Wakanda to become king, but must defend his nation when a powerful enemy challenges his rule and threatens the future of the kingdom.','2025-12-04 00:24:12',0,1,10.00),(3,'Iron Man','Jon Favreau','Mark Fergus; Hawk Ostby; Art Marcum; Matt Holloway','2008-05-01',126,'PG-13','Action; Superhero','Robert Downey Jr.; Gwyneth Paltrow; Terrence Howard; Jeff Bridges; Shaun Toub','https://i.pinimg.com/originals/ca/ad/9c/caad9c2dc610e6e32c30f0efbcf1d80c.jpg','After being captured by terrorists, Tony Stark builds a powerful armored suit to escape and later becomes the superhero Iron Man as he battles threats to the world.','2025-12-04 00:27:12',1,1,20.50);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentals`
--

DROP TABLE IF EXISTS `rentals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rentals` (
  `rental_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `reservation_id` int DEFAULT NULL,
  `checkout_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `due_at` datetime NOT NULL,
  `return_at` datetime DEFAULT NULL,
  `price_per_day` decimal(6,2) NOT NULL,
  `additional_fees` decimal(6,2) NOT NULL DEFAULT '0.00',
  `format` enum('DVD','BLURAY') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DVD',
  PRIMARY KEY (`rental_id`),
  KEY `fk_rent_user` (`user_id`),
  KEY `fk_rent_movie` (`movie_id`),
  KEY `fk_rent_res` (`reservation_id`),
  CONSTRAINT `fk_rent_movie` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_rent_res` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`) ON DELETE SET NULL,
  CONSTRAINT `fk_rent_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentals`
--

LOCK TABLES `rentals` WRITE;
/*!40000 ALTER TABLE `rentals` DISABLE KEYS */;
/*!40000 ALTER TABLE `rentals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `price_per_day` decimal(6,2) NOT NULL,
  `reservation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `return_due_at` datetime NOT NULL,
  `additional_fees` decimal(6,2) NOT NULL DEFAULT '0.00',
  `status` enum('PENDING','CONFIRMED','CANCELLED','RETURNED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `format` enum('DVD','BLURAY') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DVD',
  `return_actual_at` datetime DEFAULT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `fk_res_user` (`user_id`),
  KEY `fk_res_movie` (`movie_id`),
  CONSTRAINT `fk_res_movie` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_res_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,14,1,2.50,'2025-12-01 13:19:10','2025-12-04 13:19:10',0.00,'RETURNED','DVD','2025-12-01 21:13:56'),(2,14,1,2.50,'2025-12-01 13:19:19','2025-12-04 13:19:19',0.00,'RETURNED','BLURAY','2025-12-01 20:47:32'),(3,14,1,2.50,'2025-12-01 20:48:31','2025-12-04 20:48:31',0.00,'RETURNED','BLURAY','2025-12-01 20:48:50'),(4,14,1,2.50,'2025-12-01 20:53:17','2025-12-04 20:53:17',0.00,'RETURNED','BLURAY','2025-12-01 20:53:29'),(5,14,1,2.50,'2025-12-01 20:55:42','2025-12-04 20:55:42',0.00,'RETURNED','BLURAY','2025-12-01 20:55:53'),(6,14,1,2.50,'2025-12-01 21:49:44','2025-12-04 21:49:44',0.00,'RETURNED','DVD','2025-12-01 21:50:12'),(7,14,1,2.50,'2025-12-01 21:50:53','2025-12-04 21:50:53',0.00,'RETURNED','BLURAY','2025-12-01 21:52:52'),(8,14,1,2.50,'2025-12-01 21:53:47','2025-12-04 21:53:47',0.00,'RETURNED','DVD','2025-12-01 21:54:01'),(9,14,1,2.50,'2025-12-01 21:54:42','2025-12-04 21:54:42',0.00,'RETURNED','DVD','2025-12-01 22:31:03'),(10,14,1,2.50,'2025-12-01 22:30:44','2025-12-04 22:30:44',0.00,'RETURNED','BLURAY','2025-12-01 22:30:56'),(11,14,1,2.50,'2025-12-01 22:31:11','2025-12-04 22:31:11',0.00,'RETURNED','DVD','2025-12-01 22:31:39'),(12,14,1,2.50,'2025-12-01 23:05:50','2025-12-04 23:05:50',0.00,'RETURNED','DVD','2025-12-02 20:35:28'),(13,14,1,2.50,'2025-12-02 20:36:01','2025-12-05 20:36:01',0.00,'PENDING','BLURAY',NULL),(14,16,1,2.50,'2025-12-02 20:39:12','2025-12-05 20:39:12',0.00,'RETURNED','DVD','2025-12-02 20:40:22'),(15,16,1,2.50,'2025-12-02 20:40:42','2025-12-05 20:40:42',0.00,'RETURNED','DVD','2025-12-03 10:01:30'),(16,18,2,2.50,'2025-12-03 17:36:58','2025-12-06 17:36:58',0.00,'RETURNED','BLURAY','2025-12-03 17:38:09'),(17,14,2,2.50,'2025-12-03 17:39:26','2025-12-06 17:39:26',0.00,'PENDING','DVD',NULL),(18,18,2,2.50,'2025-12-03 18:43:37','2025-12-06 18:43:37',0.00,'RETURNED','BLURAY','2025-12-03 18:45:03'),(19,18,1,2.50,'2025-12-03 19:04:07','2025-12-06 19:04:07',0.00,'RETURNED','DVD','2025-12-03 19:04:28'),(20,18,3,2.50,'2025-12-04 11:11:46','2025-12-07 11:11:46',0.00,'RETURNED','DVD','2025-12-04 11:12:19'),(21,18,2,2.50,'2025-12-04 11:12:47','2025-12-07 11:12:47',0.00,'RETURNED','BLURAY','2025-12-04 11:13:16'),(22,18,1,2.50,'2025-12-04 12:01:13','2025-12-07 12:01:13',0.00,'RETURNED','DVD','2025-12-04 12:01:52'),(23,18,2,2.50,'2025-12-04 12:26:33','2025-12-07 12:26:33',0.00,'RETURNED','BLURAY','2025-12-04 12:27:30'),(24,18,2,2.50,'2025-12-04 13:59:30','2025-12-07 13:59:30',0.00,'RETURNED','BLURAY','2025-12-04 14:00:17');
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `middle_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `zip_code` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('USER','ADMIN') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'USER',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (14,'Isaiah','Xavier','Vega','9392908388','169 Calle Progreso','Aguadilla','00603','2003-10-22','IsaVega0802','roccO0802!','USER','2025-12-01 13:38:43'),(15,'Roberto',NULL,'Soto',NULL,NULL,'Aguadilla',NULL,'2002-10-21','RobSoto0802','roccO0802!','USER','2025-12-02 02:19:17'),(16,'julio','a','marquez',NULL,NULL,'moca',NULL,'2004-06-11','hola','hola2025','USER','2025-12-03 03:37:46'),(17,'Dora','','Exploradora','88775521','Hc castillos melendez','Aguadilla','00587','1999-08-07','Adios','Adios2025','USER','2025-12-03 17:12:51'),(18,'samira','','velazquez','78755223','hc guerrero','Ponce','00258','2004-08-17','tutoria','tutoria19','USER','2025-12-03 18:54:53');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-04 14:12:37
