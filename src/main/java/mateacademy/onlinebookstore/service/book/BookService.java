package mateacademy.onlinebookstore.service.book;

import java.util.List;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.repository.book.BookSearchParameters;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto update(Long id, CreateBookRequestDto dto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params, Pageable pageable);

    List<BookDtoWithoutCategoryIds> getAllBooksByCategoryId(Long id, Pageable pageable);
}
