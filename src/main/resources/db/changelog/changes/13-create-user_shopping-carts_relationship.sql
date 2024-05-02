ALTER TABLE shopping_carts
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES users(id);
