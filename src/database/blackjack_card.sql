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
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `idcard` int(11) NOT NULL AUTO_INCREMENT,
  `suit` varchar(45) NOT NULL,
  `figure` varchar(45) NOT NULL,
  `card_value` int(11) NOT NULL,
  PRIMARY KEY (`idcard`),
  UNIQUE KEY `idcard_UNIQUE` (`idcard`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (1,'H','A',11),(2,'H','K',10),(3,'H','Q',10),(4,'H','J',10),(5,'H','10',10),(6,'H','9',9),(7,'H','8',8),(8,'H','7',7),(9,'H','6',6),(10,'H','5',5),(11,'H','4',5),(12,'H','3',3),(13,'H','2',2),(14,'D','A',11),(15,'D','K',10),(16,'D','Q',10),(17,'D','J',10),(18,'D','10',10),(19,'D','9',9),(20,'D','8',8),(21,'D','7',7),(22,'D','6',6),(23,'D','5',5),(24,'D','4',4),(25,'D','3',3),(26,'D','2',2),(27,'C','A',11),(28,'C','K',10),(29,'C','Q',10),(30,'C','J',10),(31,'C','10',10),(32,'C','9',9),(33,'C','8',8),(34,'C','7',7),(35,'C','6',6),(36,'C','5',5),(37,'C','4',4),(38,'C','3',3),(39,'C','2',2),(40,'S','A',11),(41,'S','K',10),(42,'S','Q',10),(43,'S','J',10),(44,'S','10',10),(45,'S','9',9),(46,'S','8',8),(47,'S','7',7),(48,'S','6',6),(49,'S','5',5),(50,'S','4',4),(51,'S','3',3),(52,'S','2',2),(53,'H','A',11),(54,'H','K',10),(55,'H','Q',10),(56,'H','J',10),(57,'H','10',10),(58,'H','9',9),(59,'H','8',8),(60,'H','7',7),(61,'H','6',6),(62,'H','5',5),(63,'H','4',5),(64,'H','3',3),(65,'H','2',2),(66,'D','A',11),(67,'D','K',10),(68,'D','Q',10),(69,'D','J',10),(70,'D','10',10),(71,'D','9',9),(72,'D','8',8),(73,'D','7',7),(74,'D','6',6),(75,'D','5',5),(76,'D','4',4),(77,'D','3',3),(78,'D','2',2),(79,'C','A',11),(80,'C','K',10),(81,'C','Q',10),(82,'C','J',10),(83,'C','10',10),(84,'C','9',9),(85,'C','8',8),(86,'C','7',7),(87,'C','6',6),(88,'C','5',5),(89,'C','4',4),(90,'C','3',3),(91,'C','2',2),(92,'S','A',11),(93,'S','K',10),(94,'S','Q',10),(95,'S','J',10),(96,'S','10',10),(97,'S','9',9),(98,'S','8',8),(99,'S','7',7),(100,'S','6',6),(101,'S','5',5),(102,'S','4',4),(103,'S','3',3),(104,'S','2',2);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-18 18:23:40
