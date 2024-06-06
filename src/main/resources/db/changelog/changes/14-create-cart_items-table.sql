CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shopping_cart_id BIGINT,
    book_id BIGINT,
    quantity INT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts(user_id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
