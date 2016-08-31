CREATE TABLE stores (
  store_id bigint NOT NULL AUTO_INCREMENT,
  name varchar(60) NOT NULL,
  address varchar(200) DEFAULT NULL,
  phone char(20) DEFAULT NULL,
  notes varchar(2048) DEFAULT NULL,
  PRIMARY KEY (store_id)
)  ;

CREATE TABLE products (
  product_id bigint NOT NULL AUTO_INCREMENT,
  store_id bigint NOT NULL,
  name varchar(45) NOT NULL,
  description varchar(200) NOT NULL,
  sku varchar(10) NOT NULL,
  price decimal(15,2) NOT NULL,
  PRIMARY KEY (product_id),
  FOREIGN KEY (store_id) REFERENCES stores (store_id)
)  ;

CREATE TABLE stock (
  product_id bigint NOT NULL,
  store_id bigint NOT NULL,
  count int(11) NOT NULL,
  PRIMARY KEY (product_id,store_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id) ,
  FOREIGN KEY (store_id) REFERENCES stores (store_id)
) ;

CREATE TABLE orders (
  order_id bigint NOT NULL AUTO_INCREMENT,
  store_id bigint NOT NULL,
  order_date timestamp NOT NULL,
  status int(11) NOT NULL,
  first_name varchar(200) NOT NULL,
  last_name varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  phone char(20) NOT NULL,
  PRIMARY KEY (order_id)
)  ;

CREATE TABLE order_products (
  product_id bigint NOT NULL,
  order_id bigint NOT NULL,
  count int(11) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders (order_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id)
)  ;
