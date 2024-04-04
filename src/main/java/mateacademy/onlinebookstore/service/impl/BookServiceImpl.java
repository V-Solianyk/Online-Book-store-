package mateacademy.onlinebookstore.service.impl;

import java.util.List;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.BookRepository;
import mateacademy.onlinebookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
