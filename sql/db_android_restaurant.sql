-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.1.26-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win32
-- HeidiSQL Versão:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para android_restaurant_database
CREATE DATABASE IF NOT EXISTS `android_restaurant_database` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `android_restaurant_database`;


-- Copiando estrutura para tabela android_restaurant_database.client_order
CREATE TABLE IF NOT EXISTS `client_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `FK_visit_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_id`),
  KEY `FK_order_user` (`FK_visit_id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`FK_visit_id`) REFERENCES `visit` (`visit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela android_restaurant_database.client_order: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `client_order` DISABLE KEYS */;
INSERT INTO `client_order` (`order_id`, `FK_visit_id`) VALUES
	(5, 1),
	(3, 2),
	(6, 2),
	(7, 2),
	(8, 2),
	(9, 2),
	(10, 2),
	(11, 2),
	(12, 2),
	(4, 3),
	(14, 5),
	(15, 5),
	(16, 6);
/*!40000 ALTER TABLE `client_order` ENABLE KEYS */;


-- Copiando estrutura para tabela android_restaurant_database.order_contains
CREATE TABLE IF NOT EXISTS `order_contains` (
  `order_contains_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `FK_product_id` int(11) NOT NULL DEFAULT '0',
  `FK_order_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_contains_id`),
  KEY `FK_order_contains_order` (`FK_order_id`),
  KEY `FK_order_contains_product` (`FK_product_id`),
  CONSTRAINT `FK_order_contains_order` FOREIGN KEY (`FK_order_id`) REFERENCES `client_order` (`order_id`),
  CONSTRAINT `FK_order_contains_product` FOREIGN KEY (`FK_product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela android_restaurant_database.order_contains: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `order_contains` DISABLE KEYS */;
INSERT INTO `order_contains` (`order_contains_id`, `FK_product_id`, `FK_order_id`) VALUES
	(1, 1, 3),
	(2, 1, 4),
	(5, 2, 4),
	(6, 1, 5),
	(8, 3, 11),
	(9, 3, 12),
	(10, 3, 12),
	(11, 3, 12),
	(12, 3, 12),
	(17, 1, 14),
	(18, 2, 14),
	(19, 3, 14),
	(20, 4, 14),
	(21, 1, 15),
	(22, 2, 15),
	(23, 3, 15),
	(24, 4, 15),
	(25, 1, 16),
	(26, 2, 16),
	(27, 3, 16),
	(28, 4, 16);
/*!40000 ALTER TABLE `order_contains` ENABLE KEYS */;


-- Copiando estrutura para tabela android_restaurant_database.product
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` char(50) NOT NULL,
  `product_type` enum('DOCE','SALGADO','SOBREMESA','BEBIDA_COMUM','BEBIDA_ALCOOLICA') NOT NULL,
  `product_description` tinytext NOT NULL,
  `product_picture` tinytext NOT NULL,
  `product_price` decimal(7,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela android_restaurant_database.product: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`product_id`, `product_name`, `product_type`, `product_description`, `product_picture`, `product_price`) VALUES
	(1, 'Produto1', 'DOCE', 'Descricao produto1', '1.png', 10.00),
	(2, 'Produto2', 'SALGADO', 'Descricao produto 2', '2.png', 55.90),
	(3, 'Produto3', 'SALGADO', 'Descricao produto 3', '3.png', 5.00),
	(4, 'Cachorro Quente', 'SALGADO', 'Cachorro quente com batata palha e purê', 'cq.png', 6.00);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- Copiando estrutura para tabela android_restaurant_database.restaurant_table
CREATE TABLE IF NOT EXISTS `restaurant_table` (
  `table_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `table_name` char(10) NOT NULL,
  `maximum_chairs` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela android_restaurant_database.restaurant_table: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `restaurant_table` DISABLE KEYS */;
INSERT INTO `restaurant_table` (`table_id`, `table_name`, `maximum_chairs`) VALUES
	(1, 'A1', 5),
	(2, 'A2', 5),
	(3, 'A3', 4),
	(4, 'A4', 3);
/*!40000 ALTER TABLE `restaurant_table` ENABLE KEYS */;


-- Copiando estrutura para tabela android_restaurant_database.visit
CREATE TABLE IF NOT EXISTS `visit` (
  `visit_id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_table` int(10) unsigned DEFAULT '1',
  `client_name` char(50) NOT NULL,
  `client_sex` enum('M','F','O') NOT NULL DEFAULT 'O',
  `people_on_table` tinyint(4) unsigned DEFAULT '1',
  `visit_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`visit_id`),
  KEY `FK_visit_table` (`FK_table`),
  CONSTRAINT `FK_visit_table` FOREIGN KEY (`FK_table`) REFERENCES `restaurant_table` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela android_restaurant_database.visit: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `visit` DISABLE KEYS */;
INSERT INTO `visit` (`visit_id`, `FK_table`, `client_name`, `client_sex`, `people_on_table`, `visit_date`) VALUES
	(1, 1, 'Kártia', 'M', 5, '2018-03-28 12:55:02'),
	(2, 1, 'Kátia', 'F', 2, '2018-03-28 12:55:02'),
	(3, 1, 'Bartolomeu', 'M', 1, '2018-03-28 12:55:02'),
	(4, 1, 'Cráudia', 'O', 3, '2018-03-28 12:55:02'),
	(5, 2, 'Craudinete', 'F', 3, '2018-03-29 10:37:11'),
	(6, 3, 'Abraão', 'M', 15, '2018-03-29 10:58:16'),
	(7, 3, 'Abraão', 'M', 15, '2018-03-29 12:13:57');
/*!40000 ALTER TABLE `visit` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
