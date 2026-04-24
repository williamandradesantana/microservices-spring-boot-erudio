package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.environment.InstanceInformationService;
import io.github.williamandradesantana.model.Book;
import io.github.williamandradesantana.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private InstanceInformationService informationService;

    @Autowired
    private BookRepository repository;

    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        String port = informationService.retrieveServerPort();

        Book book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book with id: " + id + " not found!"));
        book.setEnvironment(port);
        book.setCurrency(currency);
        return ResponseEntity.ok().body(book);
    }
}
