package mateacademy.onlinebookstore.repository;

import mateacademy.onlinebookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<Book> getSpecification(String[] params);
}
