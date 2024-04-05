package mateacademy.onlinebookstore.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.BookDto;
import mateacademy.onlinebookstore.dto.CreateBookRequestDto;
import mateacademy.onlinebookstore.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookDto create(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }
}
