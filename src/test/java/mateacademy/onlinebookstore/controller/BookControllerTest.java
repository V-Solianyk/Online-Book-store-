package mateacademy.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.service.book.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext webApplicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Create a new book")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/category/remove-categories.sql",
            "/database/books/remove-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_ValidRequest_Ok() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Kobzar");
        requestDto.setAuthor("Taras Shevchenko");
        requestDto.setDescription("About history of Ukraine");
        requestDto.setIsbn("0212-32-33");
        requestDto.setPrice(BigDecimal.valueOf(45.55));
        requestDto.setCategories(Set.of(1L));
        requestDto.setCoverImage("default image");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = mapper.readValue(mvcResult.getResponse()
                .getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals("Kobzar", actual.getTitle());
        Assertions.assertEquals("Taras Shevchenko", actual.getAuthor());
        Assertions.assertEquals("About history of Ukraine", actual.getDescription());
        Assertions.assertEquals("0212-32-33", actual.getIsbn());
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Create a new book")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/category/remove-categories.sql",
            "/database/books/remove-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_InvalidRequestEmptyTitle_ShouldThrowException() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("");
        requestDto.setAuthor("Taras Shevchenko");
        requestDto.setDescription("About history of Ukraine");
        requestDto.setIsbn("0212-32-33");
        requestDto.setPrice(BigDecimal.valueOf(45.55));
        requestDto.setCategories(Set.of(1L));
        requestDto.setCoverImage("default image");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String[] splitActualResult = mvcResult.getResponse().getContentAsString().split(",");
        String[] onlyTextsFromExceptionBody = splitActualResult[2].split(":");
        String message = onlyTextsFromExceptionBody[1]
                .substring(0, onlyTextsFromExceptionBody[1].length() - 1);
        Assertions.assertNotNull(message);
        Assertions.assertEquals("[\"title must not be blank\"]", message);
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Create a new book")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/category/remove-categories.sql",
            "/database/books/remove-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_InvalidRequestEmptyAuthor_ShouldThrowException() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Kobzar");
        requestDto.setAuthor("");
        requestDto.setDescription("About history of Ukraine");
        requestDto.setIsbn("0212-32-33");
        requestDto.setPrice(BigDecimal.valueOf(45.55));
        requestDto.setCategories(Set.of(1L));
        requestDto.setCoverImage("default image");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String[] splitActualResult = mvcResult.getResponse().getContentAsString().split(",");
        String[] onlyTextsFromExceptionBody = splitActualResult[2].split(":");
        String message = onlyTextsFromExceptionBody[1]
                .substring(0, onlyTextsFromExceptionBody[1].length() - 1);
        Assertions.assertNotNull(message);
        Assertions.assertEquals("[\"author must not be blank\"]", message);
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Create a new book")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/category/remove-categories.sql",
            "/database/books/remove-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_InvalidRequestEmptyIsbn_ShouldThrowException() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Kobzar");
        requestDto.setAuthor("Taras Shevchenko");
        requestDto.setDescription("About history of Ukraine");
        requestDto.setIsbn("");
        requestDto.setPrice(BigDecimal.valueOf(45.55));
        requestDto.setCategories(Set.of(1L));
        requestDto.setCoverImage("default image");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String[] splitActualResult = mvcResult.getResponse().getContentAsString().split(",");
        String[] onlyTextsFromExceptionBody = splitActualResult[2].split(":");
        String message = onlyTextsFromExceptionBody[1]
                .substring(0, onlyTextsFromExceptionBody[1].length() - 1);
        Assertions.assertNotNull(message);
        Assertions.assertEquals("[\"isbn must not be blank\"]", message);
    }

    @WithMockUser(username = "user@mail.ua", password = "12345678")
    @Test
    @DisplayName("Get all books")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql",
            "/database/books/add-books-by-category-to-books-table.sql ",
            "/database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/book_categories/remove-bookId-categoryId.sql",
            "/database/books/remove-books.sql", "/database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_getThreeBooks_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto[] actual = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                BookDto[].class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(3, actual.length);
    }

    @WithMockUser(username = "user@mail.ua", password = "12345678")
    @Test
    @DisplayName("Get the book by id")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql",
            "/database/books/add-books-by-category-to-books-table.sql ",
            "/database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/book_categories/remove-bookId-categoryId.sql",
            "/database/books/remove-books.sql", "/database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getById_ReturnsBook_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = mapper.readValue(mvcResult.getResponse()
                .getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("A History of Ukraine Part 2", actual.getTitle());
        Assertions.assertEquals("Paul Robert Magocsi", actual.getAuthor());
        Assertions.assertEquals("978-0801480932", actual.getIsbn());
        Assertions.assertEquals("This comprehensive history of Ukraine ", actual.getDescription());
    }

    @WithMockUser(username = "user@mail.ua", password = "12345678")
    @Test
    @DisplayName("Get the book by id")
    void getById_NotExistBookId_ShouldThrowException() throws Exception {
        long id = 9999L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/9999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Exception.class);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Can't find a book with id : " + id, exception.getMessage());
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Delete the book by id")
    @Sql(scripts = {"classpath:/database/category/add-categories-to-categories-table.sql",
            "/database/books/add-books-by-category-to-books-table.sql ",
            "/database/book_categories/add-bookId-categoryId-to-book_categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/book_categories/remove-bookId-categoryId.sql",
            "/database/books/remove-books.sql", "/database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_DeleteBookById_Ok() throws Exception {
        mockMvc.perform(
                        delete("/api/books/3")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @WithMockUser(username = "user@mail.ua", password = "12345678")
    @Test
    @DisplayName("Get the book by id")
    void delete_NotExistBookId_ShouldThrowException() throws Exception {
        long id = 9999L;
        MvcResult mvcResult = mockMvc.perform(
                        delete("/api/books/9999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Exception.class);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Can't find a book with id : " + id, exception.getMessage());
    }
}
