DELETE FROM book_categories
 WHERE (book_id, category_id) IN ((1,1), (2,1),(3,2));
