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
        Book book = getBookById(id);

        return bookMapper.toDto(book);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto dto) {
        Book bookById = getBookById(id);
        bookById.setTitle(dto.getTitle());
        bookById.setAuthor(dto.getAuthor());
        bookById.setIsbn(dto.getIsbn());
        bookById.setPrice(dto.getPrice());
        bookById.setDescription(dto.getDescription());
        bookById.setCoverImage(dto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(bookById));
    }

    @Override
    public void deleteById(Long id) {
        Book book = getBookById(id);
        book.setDeleted(true);
        bookRepository.save(book);
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a book with"
                        + " id : " + id));
    }
}
