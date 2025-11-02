CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    phone_number VARCHAR(15),
    avatar_url VARCHAR(512),
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    verification_code VARCHAR(10),
    verification_expiration TIMESTAMP
);

CREATE TABLE products
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
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


