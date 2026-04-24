package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.dto.Exchange;
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
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

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

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().getForEntity(
                "http://localhost:8000/exchange-service/" + "{amount}/{from}/{to}",
                Exchange.class, params);

        Exchange exchange = response.getBody();
        book.setEnvironment(port);
        book.setPrice(exchange.getConvertedValue());
        book.setCurrency(currency);
        return ResponseEntity.ok().body(book);
    }
}
