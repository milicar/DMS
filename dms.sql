/*
SQLyog Community v12.4.3 (64 bit)
MySQL - 5.7.9-log : Database - dms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dms`;



/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `activity_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(50) DEFAULT NULL,
  `parent_process_id` bigint(15) NOT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `activity_fk1` (`parent_process_id`),
  CONSTRAINT `activity_fk1` FOREIGN KEY (`parent_process_id`) REFERENCES `process` (`process_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activity` */


/*Table structure for table `activity_document_type` */

DROP TABLE IF EXISTS `activity_document_type`;

CREATE TABLE `activity_document_type` (
  `activity_id` bigint(15) NOT NULL,
  `document_type_id` bigint(15) NOT NULL,
  `direction` varchar(10) NOT NULL,
  PRIMARY KEY (`activity_id`, `document_type_id`),
  KEY `activity_doctype_fk1` (`activity_id`),
  KEY `activity_doctype_fk2` (`document_type_id`),
  CONSTRAINT `activity_doctype_fk1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_doctype_fk2` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`document_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `activity_document_type` */


/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `company_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(40) DEFAULT NULL,
  `company_address` varchar(100) DEFAULT NULL,
  `company_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `company` */

insert into `company`(`company_id`,`company_name`,`company_address`,`company_description`) values
(1,'Company1','Company1 address','Company1 description'),
(2,'Company2','Company2 address','Company2 description'),
(3,'Company3','Company3 address','Company3 description'),
(4,'Company4','Company4 address','Company4 description');


/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `contact_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `contact_name` varchar(40) DEFAULT NULL,
  `contact_info` varchar(100) DEFAULT NULL,
  `contact_address` varchar(100) DEFAULT NULL,
  `contact_email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `contact` */

insert into `contact`(`contact_id`,`contact_name`,`contact_info`,`contact_address`,`contact_email`) values
(1,'Contact1','Contact1 info','contact1 address','contact1@somecompany.com'),
(2,'Contact2','Contact2 info','contact2 address','contact2@othercompany.com'),
(3,'Contact3','Contact3 info','contact3 address','contact3@supercompany.com'),
(4,'Contact4','Contact4 info','contact4 address','contact4@amazingcompany.com');


/*Table structure for table `document` */

DROP TABLE IF EXISTS `document`;

CREATE TABLE `document` (
  `document_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `document_location` varchar(100) DEFAULT NULL,
  `document_name` varchar(50) DEFAULT NULL,
  `author_id` bigint(15) DEFAULT NULL,
  `document_type_id` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `document_fk1` (`author_id`),
  KEY `document_fk2` (`document_type_id`),
  CONSTRAINT `document_fk1` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `document_fk2` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`document_type_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `document` */

insert into `document`(`document_id`, `document_location`, `document_name`, `author_id`, `document_type_id`) values
(1,'/home','doc1',1,1),
(2,'/home','doc2',2,2),
(3,'/home','doc3',3,3),
(4,'/home','doc4',3,2);

/*Table structure for table `document_descriptor` */

DROP TABLE IF EXISTS `document_descriptor`;

CREATE TABLE `document_descriptor` (
  `descriptor_id` bigint(15) NOT NULL,
  `descriptor_value` varchar(50) DEFAULT NULL,
  `document_id` bigint(15) NOT NULL,
  PRIMARY KEY (`descriptor_id`,`document_id`),
  KEY `doc_descriptor_fk1` (`descriptor_id`),
  KEY `doc_descriptor_fk2` (`document_id`),
  CONSTRAINT `doc_descriptor_fk1` FOREIGN KEY (`descriptor_id`) REFERENCES `document_type_descriptor` (`doctype_descriptor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `doc_descriptor_fk2` FOREIGN KEY (`document_id`) REFERENCES `document` (`document_id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `document_descriptor` */


/*Table structure for table `document_type_descriptor` */

DROP TABLE IF EXISTS `document_type_descriptor`;

CREATE TABLE `document_type_descriptor` (
  `doctype_descriptor_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `doctype_descriptor_name` varchar(30) DEFAULT NULL,
  `doctype_id` bigint(15) NOT NULL,
  PRIMARY KEY (`doctype_descriptor_id`),
  KEY `doctype_fk` (`doctype_id`),
  CONSTRAINT `doctype_fk` FOREIGN KEY (`doctype_id`) REFERENCES `document_type` (`document_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `document_type_descriptor` */

insert into `document_type_descriptor`(`doctype_descriptor_id`, `doctype_descriptor_name`, `doctype_id`) values
(1,'SuperDescriptorForDT1',1),
(2,'ImportantDescriptorForDT3',3),
(3,'OptionalDescriptorForDT2',2),
(4,'AdditionalDescriptorForDT2',2);

/*Table structure for table `document_type` */

DROP TABLE IF EXISTS `document_type`;

CREATE TABLE `document_type` (
  `document_type_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `model_location` varchar(100) DEFAULT NULL,
  `short_description` text,
  PRIMARY KEY (`document_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `document_type` */

insert  into `document_type`(`document_type_id`,`name`,`model_location`,`short_description`) values
(1,'DocType1','/','DocType1 is awesome.'),
(2,'DocType2','/','DocType2 is very useful.'),
(3,'DocType3','/', 'DocType3 is fundamental.');


/*Table structure for table `process` */

DROP TABLE IF EXISTS `process`;

CREATE TABLE `process` (
  `process_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `process_name` varchar(40) DEFAULT NULL,
  `process_description` varchar(100) DEFAULT NULL,
  `primitive` boolean DEFAULT NULL,
  `company_parent` bigint(15) DEFAULT NULL,
  `process_parent` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`process_id`),
  KEY `process_fk1` (`company_parent`),
  KEY `process_fk2` (`process_parent`),
  CONSTRAINT `process_fk1` FOREIGN KEY (`company_parent`) REFERENCES `company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `process_fk2` FOREIGN KEY (`process_parent`) REFERENCES `process` (`process_id`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `process` */


/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `tag_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `tag_value` text,
  `document_id` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `tag_fk` (`document_id`),
  CONSTRAINT `tag_fk` FOREIGN KEY (`document_id`) REFERENCES `document` (`document_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tag` */

insert into `tag`(`tag_id`,`tag_value`,`document_id`) values
(1,'tag1 for doc1',1),
(2,'tag2 for doc1',1),
(3,'tag1 for doc2',2),
(4,'tag2 for doc2',2),
(5,'tag3 for doc2',2),
(6,'tag1 for doc3',3);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(15) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(40) DEFAULT NULL,
  `last_name` varchar(40) DEFAULT NULL,
  `company_id` bigint(15) DEFAULT NULL,
  `username` varchar(40) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `user_role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`) ,
  KEY `user_fk` (`company_id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert into `user`(`user_id`,`first_name`,`last_name`,`company_id`,`username`,`password`,`email`,`user_role`) values
(1,'Jane','Brown',1,'janebrown','pass','jbrown@company1.com','ADMIN'),
(2,'Mary','White',1,'marywhite','pass','marywhite@company1.com','USER'),
(3,'Alice','Gray',2,'alicegray','code','alicegray@company2.com','ADMIN'),
(4,'Sarah','Blue',2,'sarahblue','word','sarahblue@company2.com','USER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;