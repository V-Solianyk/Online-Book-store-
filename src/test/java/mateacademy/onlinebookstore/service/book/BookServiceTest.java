package mateacademy.onlinebookstore.service.book;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.BookMapper;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private CreateBookRequestDto requestDto;
    private BookDto responseDto;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        Set<Long> categoryIds = new HashSet<>();
        categoryIds.add(1L);

        requestDto = new CreateBookRequestDto(
                "Example Title",
                "John Doe",
                "978-3-16-148410-0",
                BigDecimal.valueOf(29.99),
                "An example book description.",
                "example_cover.jpg",
                categoryIds
        );
        responseDto = new BookDto(1L, requestDto.getTitle(), requestDto.getAuthor(),
                requestDto.getIsbn(), requestDto.getPrice(), requestDto.getDescription(),
                requestDto.getCoverImage(), requestDto.getCategories());
    }

    @Test
    @DisplayName("Create a new book")
    void save_AddBookToDb_Ok() {
        Book book = new Book();
        when(mapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.toDto(book)).thenReturn(responseDto);

        bookService.save(requestDto);

        verify(bookRepository).save(book);
        verify(mapper).toModel(requestDto);
        verify(mapper).toDto(book);
    }

    @Test
    void findAll_GetTwoBooksFromDb_Ok() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Book> books = List.of(new Book(), new Book());
        List<BookDto> expected = List.of(new BookDto(), new BookDto());
        when(bookRepository.findAllWithCategories(pageable)).thenReturn(books);

        List<BookDto> actual = bookService.findAll(pageable);

        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void getById_GetBookFromDb_Ok() {
        Long id = 1L;
        Book book = new Book();
        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.of(book));
        when(mapper.toDto(book)).thenReturn(responseDto);

        bookService.getById(id);

        verify(bookRepository).findByIdWithCategories(id);
        verify(mapper).toDto(book);
    }

    @Test
    void getById_BookNotExistsById_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "Can't find a book with id : " + id;
        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }

    @Test
    void update_UpdatedAllBookFields_Ok() {
        Long id = 1L;
        Book bookById = new Book();
        bookById.setId(id);
        bookById.setTitle("Old Title");
        bookById.setAuthor("Old Author");
        bookById.setIsbn("Old ISBN");
        bookById.setPrice(BigDecimal.valueOf(5.99));
        bookById.setDescription("Old Description");
        bookById.setCoverImage("Old Cover Image");

        Book savedBook = new Book();
        savedBook.setId(id);
        savedBook.setTitle(requestDto.getTitle());
        savedBook.setAuthor(requestDto.getAuthor());
        savedBook.setIsbn(requestDto.getIsbn());
        savedBook.setPrice(requestDto.getPrice());
        savedBook.setDescription(requestDto.getDescription());
        savedBook.setCoverImage(requestDto.getCoverImage());

        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.of(bookById));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(mapper.toDto(savedBook)).thenReturn(responseDto);

        BookDto actualBookDto = bookService.update(id, requestDto);

        Assertions.assertEquals(responseDto.getId(), actualBookDto.getId());
        Assertions.assertEquals(responseDto.getTitle(), actualBookDto.getTitle());
        Assertions.assertEquals(responseDto.getAuthor(), actualBookDto.getAuthor());
        Assertions.assertEquals(responseDto.getIsbn(), actualBookDto.getIsbn());
        Assertions.assertEquals(responseDto.getPrice(), actualBookDto.getPrice());
        Assertions.assertEquals(responseDto.getDescription(), actualBookDto.getDescription());
        Assertions.assertEquals(responseDto.getCoverImage(), actualBookDto.getCoverImage());

        verify(bookRepository).findByIdWithCategories(id);
        verify(bookRepository).save(bookById);
        verify(mapper).toDto(savedBook);
    }

    @Test
    void update_BookNotExistsById_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "Can't find a book with id : " + id;
        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }

    @Test
    void deleteById_BookDeleted_Ok() {
        Long id = 1L;
        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.of(new Book()));

        bookService.deleteById(id);

        verify(bookRepository).findByIdWithCategories(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    void deleteById_BookDoesNotExist_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "Can't find a book with id : " + id;
        when(bookRepository.findByIdWithCategories(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }
}
