package mateacademy.onlinebookstore.service;

import java.util.List;
import mateacademy.onlinebookstore.dto.BookDto;
import mateacademy.onlinebookstore.dto.CreateBookRequestDto;
import mateacademy.onlinebookstore.repository.book.BookSearchParameters;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll();

    BookDto getById(Long id);

    BookDto update(Long id, CreateBookRequestDto dto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params);
}
