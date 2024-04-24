CREATE TABLE IF NOT EXISTS user_books (
user_id BIGINT NOT NULL,
book_id BIGINT NOT NULL,
PRIMARY KEY (user_id, book_id),
CONSTRAINT fk_user_books_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
CONSTRAINT fk_user_books_book_id FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);