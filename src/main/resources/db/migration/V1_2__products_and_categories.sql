CREATE VIEW products_and_categories AS
SELECT p.name AS product_name, c.name AS category_name
FROM products_in_categories pin
    INNER JOIN products p ON p.id = pin.id_products
    INNER JOIN categories c ON c.id = pin.id_categories;