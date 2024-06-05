package mateacademy.onlinebookstore.repository.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mateacademy.onlinebookstore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("""
            Find all books where category id is 1
            """)
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql",
            "database/books/add-books-by-category-to-books-table.sql ",
            "database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/book_categories/remove-bookId-categoryId.sql",
            "database/books/remove-books.sql", "database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_CategoryIdIs1_ReturnTwoBooks() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Book> booksByCategoryId = bookRepository.findAllByCategoryId(1L, pageable);
        assertEquals(2, booksByCategoryId.size()
        );
    }

    @Test
    @DisplayName("""
            Find all books with categories
            """)
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql",
            "database/books/add-books-by-category-to-books-table.sql ",
            "database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/book_categories/remove-bookId-categoryId.sql",
            "database/books/remove-books.sql", "database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByBooksWithCategories_ReturnThreeBooks() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Book> booksByCategoryId = bookRepository.findAllWithCategories(pageable);
        assertEquals(3, booksByCategoryId.size()
        );
    }

    @Test
    @DisplayName("""
            Find the book by id with categories
            """)
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql",
            "database/books/add-books-by-category-to-books-table.sql ",
            "database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/book_categories/remove-bookId-categoryId.sql",
            "database/books/remove-books.sql", "database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findBookByIdWithCategories_BookIdIs1_ReturnBook() {
        Long id = 1L;
        Book book = bookRepository.findByIdWithCategories(id).get();
        Assertions.assertNotNull(book);
        assertEquals("A History of Ukraine", book.getTitle());
        assertEquals(id, book.getId());
        assertEquals("978-0801480931", book.getIsbn());
        assertEquals(BigDecimal.valueOf(25.80).setScale(2), book.getPrice().setScale(2));
        assertEquals("This comprehensive history of Ukraine ", book.getDescription());
    }

    @Test
    @DisplayName("""
            Find an optional book by not existed id
            """)
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql",
            "database/books/add-books-by-category-to-books-table.sql ",
            "database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/book_categories/remove-bookId-categoryId.sql",
            "database/books/remove-books.sql", "database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findBookByIdWithCategories_BookIdIs999_ReturnEmptyOptional() {
        Long id = 999L;
        Optional<Book> book = bookRepository.findByIdWithCategories(id);
        Assertions.assertTrue(book.isEmpty());
    }
}
