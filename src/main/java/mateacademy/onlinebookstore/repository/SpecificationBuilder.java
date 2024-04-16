package mateacademy.onlinebookstore.repository;

import mateacademy.onlinebookstore.repository.book.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters bookSearchParameters);
}
