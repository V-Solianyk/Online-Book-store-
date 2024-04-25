package mateacademy.onlinebookstore.service.category;

import java.util.List;
import mateacademy.onlinebookstore.dto.category.CategoryRequestDto;
import mateacademy.onlinebookstore.dto.category.CategoryResponseDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
