package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.environment.InstanceInformationService;
import io.github.williamandradesantana.model.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange-service")
public class ExchangeController {

    @Autowired
    private InstanceInformationService informationService;

    @GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exchange> getExchange(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        Exchange exchange = new Exchange(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, informationService.retrieveServerPort());
        return ResponseEntity.ok().body(exchange);
    }
}
