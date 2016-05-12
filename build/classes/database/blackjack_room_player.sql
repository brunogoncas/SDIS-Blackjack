-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: blackjack
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `room_player`
--

DROP TABLE IF EXISTS `room_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_player` (
  `idroom_player` int(11) NOT NULL AUTO_INCREMENT,
  `idroom` int(11) NOT NULL,
  `idplayer` int(11) NOT NULL,
  PRIMARY KEY (`idroom_player`),
  UNIQUE KEY `idroom_player_UNIQUE` (`idroom_player`),
  KEY `idplayer_idx` (`idplayer`),
  KEY `idroom_idx` (`idroom`),
  CONSTRAINT `idplayer` FOREIGN KEY (`idplayer`) REFERENCES `players` (`idplayers`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `idroom` FOREIGN KEY (`idroom`) REFERENCES `room` (`idroom`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_player`
--

LOCK TABLES `room_player` WRITE;
/*!40000 ALTER TABLE `room_player` DISABLE KEYS */;
INSERT INTO `room_player` VALUES (14,7,21),(15,8,21),(16,9,21),(17,11,21),(21,12,21),(22,13,21),(23,13,21),(24,13,21),(25,14,21),(26,15,21),(27,16,21),(28,17,21),(29,18,21),(30,19,21),(31,20,21),(32,21,21);
/*!40000 ALTER TABLE `room_player` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-12 17:35:59
