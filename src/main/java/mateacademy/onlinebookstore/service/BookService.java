package mateacademy.onlinebookstore.service;

import java.util.List;
import mateacademy.onlinebookstore.dto.BookDto;
import mateacademy.onlinebookstore.dto.CreateBookRequestDto;
import mateacademy.onlinebookstore.repository.book.BookSearchParameters;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto update(Long id, CreateBookRequestDto dto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params, Pageable pageable);
}
