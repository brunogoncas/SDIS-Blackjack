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
-- Table structure for table `hand_dealer`
--

DROP TABLE IF EXISTS `hand_dealer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hand_dealer` (
  `idhand_dealer` int(11) NOT NULL AUTO_INCREMENT,
  `iddealer` int(11) NOT NULL,
  `idcards` int(11) NOT NULL,
  PRIMARY KEY (`idhand_dealer`),
  UNIQUE KEY `idhand_dealer_UNIQUE` (`idhand_dealer`),
  KEY `dealer_id_idx` (`iddealer`),
  KEY `card_id_idx` (`idcards`),
  CONSTRAINT `card_id` FOREIGN KEY (`idcards`) REFERENCES `card` (`idcard`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dealer_id` FOREIGN KEY (`iddealer`) REFERENCES `dealer` (`iddealer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hand_dealer`
--

LOCK TABLES `hand_dealer` WRITE;
/*!40000 ALTER TABLE `hand_dealer` DISABLE KEYS */;
INSERT INTO `hand_dealer` VALUES (1,1,20),(2,1,24),(3,1,17),(4,3,12),(5,3,52),(6,3,46),(7,3,42),(8,4,46),(9,4,20),(10,4,13),(11,6,3),(12,6,32),(13,7,23),(14,7,25),(15,7,23),(16,7,44),(17,8,3),(18,8,35),(19,8,24),(20,9,31),(21,9,31),(22,10,26),(23,10,1),(24,10,40),(25,11,44),(26,11,10),(27,11,16),(28,12,44),(29,12,1),(30,13,38),(31,13,38),(32,13,48),(33,13,38),(34,13,31),(35,14,40),(36,14,50),(37,15,6),(38,15,6),(39,16,51),(40,16,35),(41,16,24),(42,16,19),(43,16,46),(44,16,49),(45,16,6),(46,16,37),(47,16,10),(48,16,32),(49,16,9),(50,16,43),(51,17,19),(52,17,45),(53,17,5),(54,17,3);
/*!40000 ALTER TABLE `hand_dealer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-15 19:47:05
