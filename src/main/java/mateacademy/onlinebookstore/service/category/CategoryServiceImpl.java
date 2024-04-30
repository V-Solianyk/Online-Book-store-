package mateacademy.onlinebookstore.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.category.CategoryRequestDto;
import mateacademy.onlinebookstore.dto.category.CategoryResponseDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.CategoryMapper;
import mateacademy.onlinebookstore.model.Category;
import mateacademy.onlinebookstore.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = getCategoryById(id);
        return mapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryDto) {
        Category category = mapper.toModel(categoryDto);
        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryDto) {
        Category category = getCategoryById(id);
        category.setName(categoryDto.getName());
        if (categoryDto.getDescription() != null) {
            category.setDescription(category.getDescription());
        }
        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        Category category = getCategoryById(id);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category doesn't "
                        + "exist by this id: " + id));
    }
}
