DROP VIEW IF EXISTS products_and_categories;

ALTER TABLE products ADD COLUMN IF NOT EXISTS image VARCHAR(255);

CREATE OR REPLACE VIEW products_and_categories AS
SELECT p.name AS product_name,
       p.image AS image_name,
       c.name AS category_name
FROM products_in_categories pin
         INNER JOIN products p ON p.id = pin.id_products
         INNER JOIN categories c ON c.id = pin.id_categories;