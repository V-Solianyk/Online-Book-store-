package mateacademy.onlinebookstore.service;

import java.util.List;
import mateacademy.onlinebookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
