DELETE FROM orders;
DELETE FROM order_products;
DELETE FROM stock;
DELETE FROM products;
DELETE FROM stores;

-- BEGIN creating STORES initial data
INSERT INTO stores (name,address,phone,notes) VALUES ('Initial Store 1','123 Main St.', '2222222222','this is a test store, please handle with care');
INSERT INTO stores (name,address,phone,notes) VALUES ('Initial Store 2','456 Elm St.', '9999999999','this is a test store, please handle with care');
-- END creating STORES initial data



-- BEGIN creating PRODUCTS initial data
INSERT INTO products (store_id,name,description,sku,price) VALUES (1,'Test Product 1', 'great product 1','123',3.25);
INSERT INTO products (store_id,name,description,sku,price) VALUES (1,'Test Product 2', 'great product 2','456',8.50);
INSERT INTO products (store_id,name,description,sku,price) VALUES (2,'Test Product 3', 'great product 3','789',9.50);
INSERT INTO products (store_id,name,description,sku,price) VALUES (2,'Test Product 4', 'great product 4','999',7.50);
INSERT INTO products (store_id,name,description,sku,price) VALUES (2,'Test Product 5', 'great product 5','789',6.50);
-- END creating PRODUCTS initial data






