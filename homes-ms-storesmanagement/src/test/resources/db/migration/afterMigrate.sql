DELETE FROM orders;
DELETE FROM order_products;
DELETE FROM stock;
DELETE FROM products;
DELETE FROM stores;

-- BEGIN creating STORES initial data
INSERT INTO stores (name,address,phone,notes) VALUES ('Initial Store 1','123 MAis St.', '2222222222','this is a test store, please handle with care');
-- END creating STORES initial data

