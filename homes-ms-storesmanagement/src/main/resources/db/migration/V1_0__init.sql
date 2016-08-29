CREATE TABLE `stores` (
  `store_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` char(20) DEFAULT NULL,
  `notes` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `products` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `name_key` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `sku` varchar(10) NOT NULL,
  `price` decimal(15,2) NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `name_key_UNIQUE` (`name_key`),
  KEY `fk_products_stores1_idx` (`store_id`),
  CONSTRAINT `fk_products_stores1` FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `stock` (
  `product_id` bigint(20) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`store_id`),
  KEY `fk_stock_stores1_idx` (`store_id`),
  CONSTRAINT `fk_stock_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_stores1` FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` char(20) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `order_products` (
  `product_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `count` int(11) NOT NULL,
  KEY `fk_order_products_products1_idx` (`product_id`),
  KEY `fk_order_products_orders1_idx` (`order_id`),
  CONSTRAINT `fk_order_products_orders1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_products_products1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
