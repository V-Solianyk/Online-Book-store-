package mateacademy.onlinebookstore.service.category;

import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.category.CategoryRequestDto;
import mateacademy.onlinebookstore.dto.category.CategoryResponseDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.CategoryMapper;
import mateacademy.onlinebookstore.model.Category;
import mateacademy.onlinebookstore.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(mapper::toDto);
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
        category.setDescription(category.getDescription());

        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        getCategoryById(id);
        categoryRepository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category doesn't "
                        + "exist by this id: " + id));
    }
}
