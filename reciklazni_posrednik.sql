-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: reciklazni_posrednik
-- ------------------------------------------------------
-- Server version	5.5.56-log

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
-- Table structure for table `izvestaj`
--

DROP TABLE IF EXISTS `izvestaj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `izvestaj` (
  `ID` int(11) NOT NULL,
  `datum` date DEFAULT NULL,
  `sluzba_PIB` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PIB_idx` (`sluzba_PIB`),
  CONSTRAINT `FK1d42vg7ahbqgphybdh1bqhbhy` FOREIGN KEY (`sluzba_PIB`) REFERENCES `sluzba_transporta` (`PIB`),
  CONSTRAINT `PIB` FOREIGN KEY (`sluzba_PIB`) REFERENCES `sluzba_transporta` (`PIB`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `izvestaj`
--

LOCK TABLES `izvestaj` WRITE;
/*!40000 ALTER TABLE `izvestaj` DISABLE KEYS */;
/*!40000 ALTER TABLE `izvestaj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnik` (
  `brLicne` varchar(20) NOT NULL,
  `ime` varchar(40) DEFAULT NULL,
  `prezime` varchar(60) DEFAULT NULL,
  `user` varchar(15) DEFAULT NULL,
  `pass` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `grad` varchar(30) DEFAULT NULL,
  `ulica` varchar(30) DEFAULT NULL,
  `broj` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`brLicne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES ('1234567891011','Dragana','Draganic','gaga','gaga','gaga@ceca.com','Beograd','27.marta','48'),('1234567894125','Milorad','Miloradic','mikica','MIKICA','milorad@gmail.com','Nis','Bul. Zorana Djindjica','65'),('1414141414141','Julija','Ciric','dzuli','Julija044','julijaciric93@hotmail.com','Nis','Gabrovacka','4'),('3232323232323','Ana','Anic','ana','ana','ana@hotmail.com','Nis','Llala','65');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sluzba_transporta`
--

DROP TABLE IF EXISTS `sluzba_transporta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sluzba_transporta` (
  `PIB` int(11) NOT NULL,
  `naziv` varchar(30) CHARACTER SET latin1 DEFAULT NULL,
  `PTT` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `sifra` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PIB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sluzba_transporta`
--

LOCK TABLES `sluzba_transporta` WRITE;
/*!40000 ALTER TABLE `sluzba_transporta` DISABLE KEYS */;
INSERT INTO `sluzba_transporta` VALUES (1,'\"Medijana\"','18000','1'),(2,'\"Gorica\"','11000','2');
/*!40000 ALTER TABLE `sluzba_transporta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavka_izvestaja`
--

DROP TABLE IF EXISTS `stavka_izvestaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stavka_izvestaja` (
  `redniBroj` int(11) NOT NULL,
  `izvestaj_ID` int(11) NOT NULL,
  `kolicina` double DEFAULT NULL,
  `materijal` varchar(30) DEFAULT NULL,
  `korisnik_brLicne` varchar(20) DEFAULT NULL,
  `zaduzenjaID` int(30) DEFAULT NULL,
  `datum` date DEFAULT NULL,
  `zaduzenja_zaduzenjaID` int(11) DEFAULT NULL,
  PRIMARY KEY (`redniBroj`,`izvestaj_ID`),
  KEY `brLicne` (`korisnik_brLicne`),
  KEY `stavka_izvestaja_ibfk_1` (`izvestaj_ID`),
  KEY `zaduzenjaID_idx` (`zaduzenjaID`),
  KEY `FKgadjmdj4bw8l21waqygetghlx` (`zaduzenja_zaduzenjaID`),
  CONSTRAINT `FKgadjmdj4bw8l21waqygetghlx` FOREIGN KEY (`zaduzenja_zaduzenjaID`) REFERENCES `zaduzenja` (`zaduzenjaID`),
  CONSTRAINT `FK86cxk62yyy3i4fi89m12ahua3` FOREIGN KEY (`izvestaj_ID`) REFERENCES `izvestaj` (`ID`),
  CONSTRAINT `FKc5hev0nkombf0circsbu420uc` FOREIGN KEY (`korisnik_brLicne`) REFERENCES `korisnik` (`brLicne`),
  CONSTRAINT `FKkljgsnxna1kd8ptc7dnw2lt58` FOREIGN KEY (`izvestaj_ID`) REFERENCES `izvestaj` (`ID`),
  CONSTRAINT `stavka_izvestaja_ibfk_1` FOREIGN KEY (`izvestaj_ID`) REFERENCES `izvestaj` (`ID`),
  CONSTRAINT `stavka_izvestaja_ibfk_2` FOREIGN KEY (`korisnik_brLicne`) REFERENCES `korisnik` (`brLicne`),
  CONSTRAINT `zaduzenjaID` FOREIGN KEY (`zaduzenjaID`) REFERENCES `zaduzenja` (`zaduzenjaID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavka_izvestaja`
--

LOCK TABLES `stavka_izvestaja` WRITE;
/*!40000 ALTER TABLE `stavka_izvestaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `stavka_izvestaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavka_zahteva`
--

DROP TABLE IF EXISTS `stavka_zahteva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stavka_zahteva` (
  `redniBroj` int(11) NOT NULL,
  `zahtev_zahtevID` int(11) NOT NULL DEFAULT '0',
  `kolicina` double DEFAULT NULL,
  `materijal` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`redniBroj`,`zahtev_zahtevID`),
  KEY `zahtev_zahtevID_idx` (`zahtev_zahtevID`),
  CONSTRAINT `FK4x0h9wtofcxqxe7smlm22jpi9` FOREIGN KEY (`zahtev_zahtevID`) REFERENCES `zahtev` (`zahtevID`),
  CONSTRAINT `zahtevID` FOREIGN KEY (`zahtev_zahtevID`) REFERENCES `zahtev` (`zahtevID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavka_zahteva`
--

LOCK TABLES `stavka_zahteva` WRITE;
/*!40000 ALTER TABLE `stavka_zahteva` DISABLE KEYS */;
/*!40000 ALTER TABLE `stavka_zahteva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zaduzenja`
--

DROP TABLE IF EXISTS `zaduzenja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zaduzenja` (
  `zaduzenjaID` int(30) NOT NULL,
  `datum` date DEFAULT NULL,
  `sluzba_PIB` int(11) DEFAULT NULL,
  `zahtev_zahtevID` int(11) DEFAULT NULL,
  `stanje` tinyint(1) DEFAULT '0',
  `checked` bit(1) NOT NULL,
  PRIMARY KEY (`zaduzenjaID`),
  KEY `zaduzenja_ibfk_1` (`sluzba_PIB`),
  KEY `zahtevID_idx` (`zahtev_zahtevID`),
  CONSTRAINT `FK5dh3pvygh7s1337tlkbj7kb1x` FOREIGN KEY (`sluzba_PIB`) REFERENCES `sluzba_transporta` (`PIB`),
  CONSTRAINT `FKbbq3uy7ciu2chtxb88iw1vc0t` FOREIGN KEY (`zahtev_zahtevID`) REFERENCES `zahtev` (`zahtevID`),
  CONSTRAINT `sluzba_PIB` FOREIGN KEY (`sluzba_PIB`) REFERENCES `sluzba_transporta` (`PIB`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zahtev_zahtevID` FOREIGN KEY (`zahtev_zahtevID`) REFERENCES `zahtev` (`zahtevID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zaduzenja`
--

LOCK TABLES `zaduzenja` WRITE;
/*!40000 ALTER TABLE `zaduzenja` DISABLE KEYS */;
/*!40000 ALTER TABLE `zaduzenja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zahtev`
--

DROP TABLE IF EXISTS `zahtev`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zahtev` (
  `zahtevID` int(11) NOT NULL,
  `datum` date DEFAULT NULL,
  `korisnik_brLicne` varchar(20) DEFAULT NULL,
  `ukupno` double DEFAULT '0',
  PRIMARY KEY (`zahtevID`),
  KEY `zahtev_ibfk_1` (`korisnik_brLicne`),
  CONSTRAINT `FK55r9l6567x2wss0gd7pe97xwh` FOREIGN KEY (`korisnik_brLicne`) REFERENCES `korisnik` (`brLicne`),
  CONSTRAINT `zahtev_ibfk_1` FOREIGN KEY (`korisnik_brLicne`) REFERENCES `korisnik` (`brLicne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zahtev`
--

LOCK TABLES `zahtev` WRITE;
/*!40000 ALTER TABLE `zahtev` DISABLE KEYS */;
/*!40000 ALTER TABLE `zahtev` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-06  0:18:35
