CREATE TABLE IF NOT EXISTS book_categories (
    book_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    CONSTRAINT fk_book_categories_book_id FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    CONSTRAINT fk_book_categories_category_id FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);
