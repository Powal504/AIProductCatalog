DROP VIEW IF EXISTS products_and_categories;

ALTER TABLE products ADD COLUMN IF NOT EXISTS image VARCHAR(255);

CREATE OR REPLACE VIEW products_and_categories AS
SELECT
    p.id,
    p.name AS product_name,
    p.image AS image_name,
    c.name AS category_name
FROM products p
         LEFT JOIN products_in_categories pin ON p.id = pin.id_products
         LEFT JOIN categories c ON c.id = pin.id_categories;