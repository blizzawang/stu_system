/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.5.62 : Database - emp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`emp` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `emp`;

/*Table structure for table `t_stu` */

DROP TABLE IF EXISTS `t_stu`;

CREATE TABLE `t_stu` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `photopath` varchar(60) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=gbk;

/*Data for the table `t_stu` */

insert  into `t_stu`(`id`,`name`,`photopath`,`sex`,`age`) values (36,'aaaaa',NULL,'m',12),(42,'zs',NULL,'m',12),(43,'www',NULL,'m',34),(44,'aaaaa',NULL,'f',13),(45,'nnnn',NULL,'m',23),(46,'liutubie',NULL,'f',25);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) DEFAULT NULL,
  `realname` varchar(60) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `status` varchar(4) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=gbk;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`realname`,`password`,`sex`,`status`,`registerTime`) values (1,'admin','admin','admin','m','1','2020-10-08 17:41:17'),(5,'ooo','','123','m','1','2020-10-08 13:03:56'),(6,'oooadd','ad','123','f','1','2020-10-08 13:13:49'),(7,'oooq','www','123','m','1','2020-10-08 14:36:58'),(8,'wwj','zhangsan','123','f','1','2020-10-09 10:53:51');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
