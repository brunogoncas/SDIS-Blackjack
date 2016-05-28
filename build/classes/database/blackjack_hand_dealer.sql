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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hand_dealer`
--

LOCK TABLES `hand_dealer` WRITE;
/*!40000 ALTER TABLE `hand_dealer` DISABLE KEYS */;
INSERT INTO `hand_dealer` VALUES (52,55,32),(53,55,44),(54,55,26),(55,55,5);
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

-- Dump completed on 2016-05-26 23:57:03
