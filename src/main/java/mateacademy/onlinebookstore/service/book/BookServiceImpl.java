package mateacademy.onlinebookstore.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.BookMapper;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.book.BookRepository;
import mateacademy.onlinebookstore.repository.book.BookSearchParameters;
import mateacademy.onlinebookstore.repository.book.BookSpecificationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllWithCategories(pageable).stream()
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
        getBookById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getAllBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategoryId(id, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private Book getBookById(Long id) {
        return bookRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a book with"
                        + " id : " + id));
    }
}
