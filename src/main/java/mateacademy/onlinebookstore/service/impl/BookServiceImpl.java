package mateacademy.onlinebookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.BookDto;
import mateacademy.onlinebookstore.dto.CreateBookRequestDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.BookMapper;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.BookRepository;
import mateacademy.onlinebookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a book with"
                        + " id : " + id));

        return bookMapper.toDto(book);
    }
}
