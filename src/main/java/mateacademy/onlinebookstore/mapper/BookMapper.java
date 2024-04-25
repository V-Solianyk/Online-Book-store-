package mateacademy.onlinebookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mateacademy.onlinebookstore.config.MapperConfig;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    default Set<Category> mapCategoryIdsToCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoriesIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoriesIds);
    }

    @AfterMapping
    default void setCategories(BookDto bookDto, @MappingTarget Book book) {
        book.setCategories(mapCategoryIdsToCategories(bookDto.getCategoryIds()));
    }
}

