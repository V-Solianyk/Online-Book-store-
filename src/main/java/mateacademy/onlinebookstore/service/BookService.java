package mateacademy.onlinebookstore.service;

import java.util.List;
import mateacademy.onlinebookstore.dto.BookDto;
import mateacademy.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll();

    BookDto getById(Long id);
}
