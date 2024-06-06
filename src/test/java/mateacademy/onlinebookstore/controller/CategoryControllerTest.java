package mateacademy.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateacademy.onlinebookstore.dto.category.CategoryRequestDto;
import mateacademy.onlinebookstore.dto.category.CategoryResponseDto;
import mateacademy.onlinebookstore.service.category.CategoryService;
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
public class CategoryControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

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
    @DisplayName("Create a new category")
    void save_ValidRequest_Ok() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("History");
        requestDto.setDescription("About history of Ukraine");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual = mapper.readValue(mvcResult.getResponse()
                .getContentAsString(), CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals("History", actual.getName());
        Assertions.assertEquals("About history of Ukraine", actual.getDescription());
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Create a new category")
    void save_InValidRequestEmptyName_ShouldThrowException() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("");
        requestDto.setDescription("About history of Ukraine");

        String jsonRequest = mapper.writeValueAsString(requestDto);

        mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Get all categories")
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_getTwoCategories_Ok() throws Exception {
        mockMvc.perform(
                        get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Get category by id")
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getById_ReturnsCategory_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryResponseDto actual = mapper.readValue(mvcResult.getResponse()
                .getContentAsString(), CategoryResponseDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Education", actual.getName());
        Assertions.assertEquals("About history of Ukraine", actual.getDescription());
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Get category by id")
    void getById_NotExistCategoryId_ShouldThrowException() throws Exception {
        long id = 9999L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories/9999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Exception.class);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("The category doesn't exist by this id: "
                + id, exception.getMessage());
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Delete the category by id")
    @Sql(scripts = {"classpath:database/category/add-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/category/remove-categories.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_DeleteCategoryById_Ok() throws Exception {
        mockMvc.perform(
                        delete("/api/categories/2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @WithMockUser(username = "admin@mail.ua", roles = {"ADMIN"}, password = "12345678")
    @Test
    @DisplayName("Get the category by id")
    void delete_NotExistCategoryId_ShouldThrowException() throws Exception {
        long id = 9999L;
        MvcResult mvcResult = mockMvc.perform(
                        delete("/api/categories/9999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Exception.class);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("The category doesn't exist by this id: " + id,
                exception.getMessage());
    }
}
