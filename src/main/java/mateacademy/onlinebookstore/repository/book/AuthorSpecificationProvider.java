package mateacademy.onlinebookstore.repository.book;

import java.util.Arrays;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "author";
    }

    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("author").in(Arrays.stream(params)));
    }
}
