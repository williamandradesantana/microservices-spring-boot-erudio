package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.dto.Exchange;
import io.github.williamandradesantana.environment.InstanceInformationService;
import io.github.williamandradesantana.model.Book;
import io.github.williamandradesantana.proxy.ExchangeProxy;
import io.github.williamandradesantana.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private InstanceInformationService informationService;

    @Autowired
    private BookRepository repository;

    @Autowired
    private ExchangeProxy exchangeProxy;

    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        String port = informationService.retrieveServerPort();

        Book book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book with id: " + id + " not found!"));

        Exchange exchange = exchangeProxy.getExchange(book.getPrice(), "USD", currency);

        book.setEnvironment(port + " FEIGN");
        book.setPrice(exchange.getConvertedValue());
        book.setCurrency(currency);
        return ResponseEntity.ok().body(book);
    }
}
