package mateacademy.onlinebookstore.mapper;

import mateacademy.onlinebookstore.config.MapperConfig;
import mateacademy.onlinebookstore.dto.book.BookDto;
import mateacademy.onlinebookstore.dto.book.CreateBookRequestDto;
import mateacademy.onlinebookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
