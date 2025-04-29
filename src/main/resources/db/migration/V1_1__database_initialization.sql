CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL DEFAULT 'user'
);

CREATE TABLE products
(
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,

    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE reviews
(
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_product INTEGER NOT NULL,
    text VARCHAR(255),
    raiting FLOAT,
    created_at DATE,

    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (id_product) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE categories
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products_in_categories
(
    id SERIAL PRIMARY KEY,
    id_products INTEGER NOT NULL,
    id_categories INTEGER NOT NULL,

    FOREIGN KEY (id_products) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (id_categories) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE favourites
(
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_products INTEGER NOT NULL,

    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (id_products) REFERENCES products(id) ON DELETE CASCADE
);


