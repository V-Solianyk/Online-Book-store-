package mateacademy.onlinebookstore.service.category;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mateacademy.onlinebookstore.dto.category.CategoryRequestDto;
import mateacademy.onlinebookstore.dto.category.CategoryResponseDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.CategoryMapper;
import mateacademy.onlinebookstore.model.Category;
import mateacademy.onlinebookstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAll_getTwoCategories_Ok() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Category> categories = List.of(new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        CategoryResponseDto dto1 = new CategoryResponseDto();
        CategoryResponseDto dto2 = new CategoryResponseDto();

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(mapper.toDto(categories.get(0))).thenReturn(dto1);
        when(mapper.toDto(categories.get(1))).thenReturn(dto2);

        Page<CategoryResponseDto> result = categoryService.findAll(pageable);

        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(dto1, result.getContent().get(0));
        Assertions.assertEquals(dto2, result.getContent().get(1));
    }

    @Test
    void getById_GetCategoryFromDb_Ok() {
        Category category = new Category();
        category.setName("Math");
        category.setDescription("Educational");

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setName(category.getName());
        expected.setDescription(category.getDescription());
        Long id = 10L;

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(mapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.getById(id);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void getById_CategoryNotExistsById_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "The category doesn't "
                + "exist by this id: " + id;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }

    @Test
    void save_AddCategoryToDb_Ok() {
        CategoryRequestDto requestDto = new CategoryRequestDto();

        Category category = new Category();

        when(mapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(new CategoryResponseDto());

        CategoryResponseDto actual = categoryService.save(requestDto);

        Assertions.assertNotNull(actual);
        verify(categoryRepository).save(category);
        verify(mapper).toModel(requestDto);
        verify(mapper).toDto(category);
    }

    @Test
    void update_updatedAllFields_Ok() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Math");
        requestDto.setDescription("Educational");

        Category category = new Category();
        category.setName("Older Math");
        category.setDescription("Older Education");

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setName(requestDto.getName());
        expected.setDescription(requestDto.getDescription());

        Long id = 10L;

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.update(id, requestDto);

        Assertions.assertEquals(actual.getName(), expected.getName());
        Assertions.assertEquals(actual.getDescription(), expected.getDescription());
    }

    @Test
    void update_CategoryNotExistsById_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "The category doesn't "
                + "exist by this id: " + id;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }

    @Test
    void deleteById_CategoryDeleted_Ok() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category()));

        categoryService.deleteById(id);

        verify(categoryRepository).findById(id);
        verify(categoryRepository).deleteById(id);
    }

    @Test
    void deleteById_CategoryNotExistsById_ShouldThrowException() {
        Long id = 999L;
        String expectedErrorMassage = "The category doesn't "
                + "exist by this id: " + id;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(id));

        Assertions.assertEquals(expectedErrorMassage, exception.getMessage());
    }
}
