package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.environment.InstanceInformationService;
import io.github.williamandradesantana.model.Book;
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

    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        String port = informationService.retrieveServerPort();
        Book book = new Book(id, "Nigel Poulton", new Date(), 15.8, "Docker Deep Dive", currency, port);
        return ResponseEntity.ok().body(book);
    }
}
