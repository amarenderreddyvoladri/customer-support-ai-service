CREATE TABLE customers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_customers_email UNIQUE (email)
);

CREATE TABLE customer_orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    order_number VARCHAR(50) NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_customer_orders_number UNIQUE (order_number)
);

CREATE TABLE support_tickets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    order_id BIGINT,
    subject VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    priority VARCHAR(30) NOT NULL,
    category VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id)
);
