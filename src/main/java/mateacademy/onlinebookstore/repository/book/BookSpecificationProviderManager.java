package mateacademy.onlinebookstore.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.repository.SpecificationProvider;
import mateacademy.onlinebookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(book -> book.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find the correct "
                        + "specification provider for key " + key));
    }
}
