package mateacademy.onlinebookstore.repository.book;

import java.util.Arrays;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("title").in(Arrays.stream(params)));
    }
}
