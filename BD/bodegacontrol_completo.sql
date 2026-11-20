-- BodegaControl - Respaldo completo (estructura + datos)


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bodegacontrol` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bodegacontrol`;
DROP TABLE IF EXISTS `detalle_ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_ventas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `producto_id` bigint NOT NULL,
  `venta_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnunx2ycrr6jfyphwmmifo6lgx` (`producto_id`),
  KEY `FK2dn1sdhrbmva44wbs9fy6l7mn` (`venta_id`),
  CONSTRAINT `FK2dn1sdhrbmva44wbs9fy6l7mn` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`),
  CONSTRAINT `FKnunx2ycrr6jfyphwmmifo6lgx` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `detalle_ventas` WRITE;
/*!40000 ALTER TABLE `detalle_ventas` DISABLE KEYS */;
INSERT INTO `detalle_ventas` (`id`, `cantidad`, `precio_unitario`, `subtotal`, `producto_id`, `venta_id`) VALUES (1,2,3.20,6.40,4,1),(2,2,5.50,11.00,15,1),(3,1,6.50,6.50,6,2),(4,2,3.00,6.00,7,3),(5,1,7.00,7.00,9,4),(6,3,4.30,12.90,10,5),(7,1,4.50,4.50,2,5),(8,2,5.50,11.00,15,6),(9,1,2.00,2.00,16,7),(10,3,4.50,13.50,17,8),(11,3,4.20,12.60,1,9),(12,1,4.50,4.50,2,10),(13,2,3.20,6.40,4,11),(14,1,6.50,6.50,6,12),(15,1,3.00,3.00,7,13),(16,1,4.50,4.50,17,13),(17,1,7.00,7.00,9,14),(18,1,4.20,4.20,1,14),(19,3,4.30,12.90,10,15),(20,1,5.50,5.50,15,16),(21,1,3.20,3.20,4,16),(22,1,2.00,2.00,16,17),(23,2,4.50,9.00,17,18),(24,1,3.00,3.00,7,18),(25,3,4.20,12.60,1,19),(26,2,7.00,14.00,9,19),(27,2,4.50,9.00,2,20),(28,2,4.30,8.60,10,20),(29,1,3.20,3.20,4,21),(30,1,5.50,5.50,15,21),(31,1,6.50,6.50,6,22),(32,2,3.00,6.00,7,23),(33,3,4.50,13.50,17,23),(34,2,7.00,14.00,9,24),(35,3,4.30,12.90,10,25),(36,3,4.50,13.50,2,25),(37,1,5.50,5.50,15,26),(38,3,2.00,6.00,16,27),(39,1,6.50,6.50,6,27),(40,3,4.50,13.50,17,28),(41,3,3.00,9.00,7,28),(42,3,4.20,12.60,1,29),(43,3,7.00,21.00,9,29),(44,1,4.50,4.50,2,30),(45,2,4.30,8.60,10,30),(46,2,3.20,6.40,4,31),(47,3,6.50,19.50,6,32),(48,2,3.00,6.00,7,33),(49,1,4.50,4.50,17,33),(50,2,7.00,14.00,9,34),(51,3,4.30,12.90,10,35),(52,1,5.50,5.50,15,36),(53,1,4.30,4.30,10,37),(54,2,4.20,8.40,1,37),(55,1,6.50,6.50,6,37),(56,2,3.80,7.60,19,38),(57,1,4.30,4.30,10,38),(58,1,4.20,4.20,1,39);
/*!40000 ALTER TABLE `detalle_ventas` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `ingresos_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingresos_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `observacion` varchar(150) DEFAULT NULL,
  `stock_anterior` int NOT NULL,
  `stock_nuevo` int NOT NULL,
  `producto_id` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5d6kwxrhl0mdruddh4fpd6ae` (`producto_id`),
  KEY `FKkmtbshnr2qmt2ypckhhghuu5q` (`usuario_id`),
  CONSTRAINT `FKf5d6kwxrhl0mdruddh4fpd6ae` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKkmtbshnr2qmt2ypckhhghuu5q` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `ingresos_stock` WRITE;
/*!40000 ALTER TABLE `ingresos_stock` DISABLE KEYS */;
INSERT INTO `ingresos_stock` (`id`, `cantidad`, `fecha`, `observacion`, `stock_anterior`, `stock_nuevo`, `producto_id`, `usuario_id`) VALUES (1,24,'2026-11-20 15:05:11.810465','Compra a distribuidor mayorista',4,28,5,1),(2,12,'2026-11-20 15:05:30.808843','Compra a distribuidor',23,35,19,1);
/*!40000 ALTER TABLE `ingresos_stock` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `categoria` enum('ABARROTES','BEBIDAS','LACTEOS','LIMPIEZA','OTROS','SNACKS') NOT NULL,
  `codigo` varchar(15) NOT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `nombre` varchar(60) NOT NULL,
  `precio_compra` decimal(10,2) NOT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `stock_minimo` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKh04wpyqwddobltuqq56cp6s05` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`id`, `activo`, `categoria`, `codigo`, `fecha_vencimiento`, `nombre`, `precio_compra`, `precio_venta`, `stock`, `stock_minimo`) VALUES (1,_binary '','ABARROTES','AB-001','2027-08-20','Arroz Costeño Extra 750 g',3.20,4.20,35,15),(2,_binary '','ABARROTES','AB-002','2027-11-20','Azúcar Rubia Cartavio 1 kg',3.60,4.50,28,12),(3,_binary '','ABARROTES','AB-003','2027-05-20','Aceite Primor Vegetal 1 L',8.90,10.50,9,10),(4,_binary '','ABARROTES','AB-004','2027-10-20','Fideos Don Vittorio Spaghetti 500 g',2.40,3.20,32,15),(5,_binary '','ABARROTES','AB-005','2028-05-20','Atún Florida Filete 170 g',4.80,6.00,28,12),(6,_binary '','BEBIDAS','BE-001','2027-03-20','Inca Kola 1.5 L',5.20,6.50,16,12),(7,_binary '','BEBIDAS','BE-002','2027-03-20','Coca Cola 500 ml',2.30,3.00,19,12),(8,_binary '','BEBIDAS','BE-003','2027-09-20','Agua San Luis 625 ml',1.10,1.50,0,12),(9,_binary '','BEBIDAS','BE-004','2027-04-20','Cerveza Pilsen 630 ml',5.50,7.00,7,10),(10,_binary '','LACTEOS','LA-001','2027-06-20','Leche Gloria Azul 400 g',3.60,4.30,42,20),(11,_binary '','LACTEOS','LA-002','2026-12-02','Yogurt Gloria Fresa 1 L',5.40,6.80,7,8),(12,_binary '','LACTEOS','LA-003','2026-11-26','Queso Fresco Bonlé 250 g',7.20,9.00,10,5),(13,_binary '','LIMPIEZA','LI-001',NULL,'Detergente Bolívar 900 g',8.70,10.90,14,6),(14,_binary '','LIMPIEZA','LI-002',NULL,'Lejía Clorox 1 L',2.90,3.80,3,6),(15,_binary '','LIMPIEZA','LI-003',NULL,'Papel Higiénico Elite x4',4.20,5.50,14,8),(16,_binary '','SNACKS','SN-001','2027-02-20','Papas Lay\'s Clásicas 42 g',1.40,2.00,30,12),(17,_binary '','SNACKS','SN-002','2027-07-20','Galletas Oreo 6 pack',3.50,4.50,13,10),(18,_binary '','SNACKS','SN-003','2027-05-20','Chocolate Sublime 30 g',1.50,2.20,0,15),(19,_binary '\0','SNACKS','PM-9243','2027-06-30','Galletas Soda Field 6 pack',2.80,3.80,35,5);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` enum('ADMIN','CAJERO') NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `activo`, `email`, `nombre`, `password`, `rol`, `username`) VALUES (1,_binary '','admin@bodegacontrol.pe','Carlos Mendoza','$2a$10$kQAw7y7vNU1n0Mk6ledfPes/cntpmRRgUNeCuAF6/rWuezh1viCgy','ADMIN','admin'),(2,_binary '','cajero@bodegacontrol.pe','María Quispe','$2a$10$sGd/N6.FLMjkn5c5KVN/quKKtJrJxiRu25NphS1vQC510ykFEAnSu','CAJERO','cajero');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) NOT NULL,
  `metodo_pago` enum('BILLETERA_DIGITAL','EFECTIVO') NOT NULL,
  `monto_recibido` decimal(10,2) DEFAULT NULL,
  `numero` varchar(12) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `vuelto` decimal(10,2) DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1ud2qhwp9bdpk3feb9hoop0jj` (`numero`),
  KEY `FKco9r9xjcdqtgd4nvnnolsr6ei` (`usuario_id`),
  CONSTRAINT `FKco9r9xjcdqtgd4nvnnolsr6ei` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` (`id`, `fecha`, `metodo_pago`, `monto_recibido`, `numero`, `total`, `vuelto`, `usuario_id`) VALUES (1,'2026-11-14 12:45:00.000000','EFECTIVO',20.00,'V-00001',17.40,2.60,2),(2,'2026-11-14 12:29:00.000000','EFECTIVO',10.00,'V-00002',6.50,3.50,2),(3,'2026-11-14 10:12:00.000000','EFECTIVO',10.00,'V-00003',6.00,4.00,1),(4,'2026-11-14 11:22:00.000000','BILLETERA_DIGITAL',7.00,'V-00004',7.00,0.00,2),(5,'2026-11-14 14:39:00.000000','EFECTIVO',20.00,'V-00005',17.40,2.60,2),(6,'2026-11-15 11:44:00.000000','EFECTIVO',20.00,'V-00006',11.00,9.00,2),(7,'2026-11-15 10:11:00.000000','EFECTIVO',10.00,'V-00007',2.00,8.00,2),(8,'2026-11-15 14:51:00.000000','EFECTIVO',20.00,'V-00008',13.50,6.50,1),(9,'2026-11-15 14:06:00.000000','BILLETERA_DIGITAL',12.60,'V-00009',12.60,0.00,2),(10,'2026-11-15 12:20:00.000000','EFECTIVO',10.00,'V-00010',4.50,5.50,2),(11,'2026-11-16 08:52:00.000000','EFECTIVO',10.00,'V-00011',6.40,3.60,2),(12,'2026-11-16 11:12:00.000000','EFECTIVO',10.00,'V-00012',6.50,3.50,2),(13,'2026-11-16 10:04:00.000000','EFECTIVO',10.00,'V-00013',7.50,2.50,1),(14,'2026-11-16 12:57:00.000000','BILLETERA_DIGITAL',11.20,'V-00014',11.20,0.00,2),(15,'2026-11-16 16:32:00.000000','EFECTIVO',20.00,'V-00015',12.90,7.10,2),(16,'2026-11-17 12:47:00.000000','EFECTIVO',10.00,'V-00016',8.70,1.30,2),(17,'2026-11-17 09:14:00.000000','EFECTIVO',10.00,'V-00017',2.00,8.00,2),(18,'2026-11-17 11:31:00.000000','EFECTIVO',20.00,'V-00018',12.00,8.00,1),(19,'2026-11-17 13:46:00.000000','BILLETERA_DIGITAL',26.60,'V-00019',26.60,0.00,2),(20,'2026-11-17 13:05:00.000000','EFECTIVO',20.00,'V-00020',17.60,2.40,2),(21,'2026-11-18 10:46:00.000000','EFECTIVO',10.00,'V-00021',8.70,1.30,2),(22,'2026-11-18 09:55:00.000000','EFECTIVO',10.00,'V-00022',6.50,3.50,2),(23,'2026-11-18 11:52:00.000000','EFECTIVO',20.00,'V-00023',19.50,0.50,1),(24,'2026-11-18 11:34:00.000000','BILLETERA_DIGITAL',14.00,'V-00024',14.00,0.00,2),(25,'2026-11-19 10:54:00.000000','EFECTIVO',30.00,'V-00025',26.40,3.60,2),(26,'2026-11-19 11:23:00.000000','EFECTIVO',10.00,'V-00026',5.50,4.50,2),(27,'2026-11-19 14:57:00.000000','EFECTIVO',20.00,'V-00027',12.50,7.50,1),(28,'2026-11-19 14:09:00.000000','BILLETERA_DIGITAL',22.50,'V-00028',22.50,0.00,2),(29,'2026-11-19 12:21:00.000000','EFECTIVO',40.00,'V-00029',33.60,6.40,2),(30,'2026-11-19 13:28:00.000000','EFECTIVO',20.00,'V-00030',13.10,6.90,1),(31,'2026-11-20 12:12:00.000000','EFECTIVO',10.00,'V-00031',6.40,3.60,2),(32,'2026-11-20 13:30:00.000000','EFECTIVO',20.00,'V-00032',19.50,0.50,2),(33,'2026-11-20 12:01:00.000000','EFECTIVO',20.00,'V-00033',10.50,9.50,1),(34,'2026-11-20 14:33:05.959149','BILLETERA_DIGITAL',14.00,'V-00034',14.00,0.00,2),(35,'2026-11-20 12:21:00.000000','EFECTIVO',20.00,'V-00035',12.90,7.10,2),(36,'2026-11-20 14:53:05.959149','EFECTIVO',10.00,'V-00036',5.50,4.50,1),(37,'2026-11-20 15:05:03.915523','EFECTIVO',20.00,'V-00037',19.20,0.80,1),(38,'2026-11-20 15:05:29.628541','EFECTIVO',20.00,'V-00038',11.90,8.10,1),(39,'2026-11-20 15:05:30.118677','BILLETERA_DIGITAL',4.20,'V-00039',4.20,0.00,2);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

