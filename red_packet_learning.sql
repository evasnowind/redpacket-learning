-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: red_packet_learning
-- ------------------------------------------------------
-- Server version	5.7.31

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
-- Table structure for table `red_packet_info`
--

DROP TABLE IF EXISTS `red_packet_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `red_packet_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `red_packet_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '红包id，采⽤\ntimestamp+5位随机数',
  `total_amount` int(11) NOT NULL DEFAULT '0' COMMENT '红包总⾦额，单位分',
  `total_packet` int(11) NOT NULL DEFAULT '0' COMMENT '红包总个数',
  `remaining_amount` int(11) NOT NULL DEFAULT '0' COMMENT '剩余红包⾦额，单位\n分',
  `remaining_packet` int(11) NOT NULL DEFAULT '0' COMMENT '剩余红包个数',
  `uid` int(20) NOT NULL DEFAULT '0' COMMENT '新建红包⽤户的⽤户标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='红包信息\n表，新建⼀个红包插⼊⼀条记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `red_packet_info`
--

LOCK TABLES `red_packet_info` WRITE;
/*!40000 ALTER TABLE `red_packet_info` DISABLE KEYS */;
INSERT INTO `red_packet_info` VALUES (8,1598373426917,20000,10,20000,10,10086,'2020-08-25 16:37:07','2020-08-25 16:37:06'),(9,1600241572064,1000,10,1000,10,10086,'2020-09-16 07:32:52','2020-09-16 07:33:18'),(10,1600241952106,2000,15,2000,15,10086,'2020-09-16 07:39:12','2020-09-16 07:39:21'),(11,1600242165929,200,5,0,0,10086,'2020-09-16 07:58:45','2020-09-16 07:58:45');
/*!40000 ALTER TABLE `red_packet_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `red_packet_record`
--

DROP TABLE IF EXISTS `red_packet_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `red_packet_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '抢到红包的⾦额',
  `nick_name` varchar(32) NOT NULL DEFAULT '0' COMMENT '抢到红包的⽤户的⽤户\n名',
  `img_url` varchar(255) NOT NULL DEFAULT '0' COMMENT '抢到红包的⽤户的头像',
  `uid` int(20) NOT NULL DEFAULT '0' COMMENT '抢到红包⽤户的⽤户标识',
  `red_packet_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '红包id，采⽤\ntimestamp+5位随机数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='抢红包记\n录表，抢⼀个红包插⼊⼀条记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `red_packet_record`
--

LOCK TABLES `red_packet_record` WRITE;
/*!40000 ALTER TABLE `red_packet_record` DISABLE KEYS */;
INSERT INTO `red_packet_record` VALUES (56,43,'陈彦斌','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg',10086,1600242165929,'2020-09-16 07:45:54','2020-09-16 07:45:53'),(57,63,'陈彦斌','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg',10086,1600242165929,'2020-09-16 07:47:02','2020-09-16 07:47:03'),(58,34,'陈彦斌','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg',10086,1600242165929,'2020-09-16 07:53:26','2020-09-16 07:53:34'),(59,19,'陈彦斌','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg',10086,1600242165929,'2020-09-16 07:56:40','2020-09-16 07:56:50'),(60,41,'陈彦斌','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg',10086,1600242165929,'2020-09-16 07:58:42','2020-09-16 07:58:42');
/*!40000 ALTER TABLE `red_packet_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(11) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '⽤户名',
  `image` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'user image',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10089 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (10086,'test','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg'),(10087,'test2','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg'),(10088,'test3','https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-16 17:04:06
