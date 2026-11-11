DROP DATABASE IF EXISTS tambo_db;
CREATE DATABASE tambo_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tambo_db;

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(100) NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    user VARCHAR(100) NOT NULL,
    password VARCHAR(100),
    role BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_users_user UNIQUE (user),
    CONSTRAINT fk_users_role FOREIGN KEY (role) REFERENCES role(id)
);

CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    address VARCHAR(200) NOT NULL,
    dni VARCHAR(10) NOT NULL,
    CONSTRAINT uq_clients_email UNIQUE (email)
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(250) NOT NULL,
    cost DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stock BIGINT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    category BIGINT NOT NULL,
    CONSTRAINT fk_products_category FOREIGN KEY (category) REFERENCES categories(id)
);

CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client BIGINT,
    date DATETIME NOT NULL,
    user BIGINT NOT NULL,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_sales_client FOREIGN KEY (client) REFERENCES clients(id),
    CONSTRAINT fk_sales_user FOREIGN KEY (user) REFERENCES users(id)
);

CREATE TABLE sale_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale BIGINT NOT NULL,
    product BIGINT NOT NULL,
    quantity BIGINT NOT NULL DEFAULT 1,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_sale_detail_sale FOREIGN KEY (sale) REFERENCES sales(id),
    CONSTRAINT fk_sale_detail_product FOREIGN KEY (product) REFERENCES products(id)
);

INSERT INTO role (role) VALUES ('admin'), ('user');

INSERT INTO users (name, lastname, user, password, role) VALUES
('Renato', 'Gutiérrez', 'rguti', '$2a$10$djrOBFnE7CHFLObiDst.6u1nm8X1owHWaoK293ln0wB6Oy/HaWypa', 1),
('Carlos', 'Ramírez', 'cramirez', '$2a$10$djrOBFnE7CHFLObiDst.6u1nm8X1owHWaoK293ln0wB6Oy/HaWypa', 2),
('María', 'Torres', 'mtorres', '$2a$10$djrOBFnE7CHFLObiDst.6u1nm8X1owHWaoK293ln0wB6Oy/HaWypa', 2);

INSERT INTO clients (name, lastname, email, address, dni) VALUES
('Juan', 'Pérez', 'juan.perez@gmail.com', 'Av. Los Olivos 123, Lima', '72845612'),
('Lucía', 'Fernández', 'lucia.fernandez@gmail.com', 'Jr. Las Flores 456, Lima', '74561238'),
('Diego', 'Ramírez', 'diego.ramirez@gmail.com', 'Av. Brasil 789, Lima', '71234589');

INSERT INTO categories (category) VALUES ('Abarrotes'), ('Lácteos'), ('Bebidas'), ('Panadería'), ('Snack'), ('Dulces'), ('Aseo');

INSERT INTO products (name, description, cost, stock, active, category) VALUES
('Pan de molde', 'Pan de molde suave', 6.80, 34, TRUE, 4),
('Inca Kola 500ml', 'Gaseosa Inca Kola de 500ml', 2.50, 50, TRUE, 3),
('Coca Cola 500ml', 'Gaseosa Coca Cola de 500ml', 2.50, 45, TRUE, 3),
('Agua San Luis 625ml', 'Agua mineral sin gas de 625ml', 1.80, 60, TRUE, 3),
('Papas Lays Clásicas 40g', 'Papas fritas Lays sabor clásico de 40g', 2.50, 35, TRUE, 5),
('Sublime Chocolate 30g', 'Chocolate Sublime con maní de 30g', 2.00, 40, TRUE, 6),
('Galletas Oreo 36g', 'Galletas Oreo con relleno de vainilla', 2.20, 30,TRUE, 6),
('Detergente Bolívar 400g', 'Detergente en polvo Bolívar de 400g', 4.90, 25, TRUE, 7),
('Papel Higiénico Elite 4 rollos', 'Paquete de papel higiénico Elite de 4 rollos', 8.50, 20, TRUE, 7),
('Leche Gloria 1L', 'Leche evaporada Gloria de 1 litro', 4.50, 30, TRUE, 2),
('Atún Florida 170g', 'Lata de atún Florida de 170g', 5.90, 25, TRUE, 1);

INSERT INTO sales (client, date, user, total) VALUES
(1, '2026-08-25 09:15:00', 2, 11.20),
(NULL, '2026-08-25 10:30:00', 2, 6.80),
(2, '2026-08-25 12:45:00', 3, 19.30),
(NULL, '2026-08-26 08:20:00', 2, 4.50),
(3, '2026-08-26 14:10:00', 3, 17.90),
(NULL, '2026-08-27 16:30:00', 2, 9.00),
(1, '2026-08-28 11:05:00', 3, 16.90),
(NULL, '2026-08-29 18:40:00', 2, 8.60),
(2, '2026-08-30 13:25:00', 3, 27.30),
(NULL, '2026-08-31 20:15:00', 2, 8.70);

INSERT INTO sale_detail (sale, product, quantity, subtotal) VALUES
-- Venta 1 - Juan Pérez
(1, 1, 2, 5.00),
(1, 5, 2, 4.00),
(1, 6, 1, 2.20),
-- Venta 2 - Sin cliente
(2, 2, 1, 2.50),
(2, 4, 1, 2.50),
(2, 3, 1, 1.80),
-- Venta 3 - Lucía Fernández
(3, 9, 2, 9.00),
(3, 10, 1, 5.90),
(3, 6, 2, 4.40),
-- Venta 4 - Sin cliente
(4, 1, 1, 2.50),
(4, 5, 1, 2.00),
-- Venta 5 - Diego Ramírez
(5, 7, 1, 4.90),
(5, 8, 1, 8.50),
(5, 9, 1, 4.50),
-- Venta 6 - Sin cliente
(6, 4, 2, 5.00),
(6, 6, 1, 2.20),
(6, 3, 1, 1.80),
-- Venta 7 - Juan Pérez
(7, 2, 2, 5.00),
(7, 5, 3, 6.00),
(7, 10, 1, 5.90),
-- Venta 8 - Sin cliente
(8, 1, 1, 2.50),
(8, 3, 2, 3.60),
(8, 4, 1, 2.50),
-- Venta 9 - Lucía Fernández
(9, 7, 2, 9.80),
(9, 8, 1, 8.50),
(9, 9, 2, 9.00),
-- Venta 10 - Sin cliente
(10, 2, 1, 2.50),
(10, 5, 2, 4.00),
(10, 6, 1, 2.20);