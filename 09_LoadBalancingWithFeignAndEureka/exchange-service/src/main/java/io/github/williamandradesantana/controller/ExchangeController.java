package io.github.williamandradesantana.controller;

import io.github.williamandradesantana.environment.InstanceInformationService;
import io.github.williamandradesantana.model.Exchange;
import io.github.williamandradesantana.repository.ExchangeRepository;
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

    @Autowired
    private ExchangeRepository repository;

    @GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exchange> getExchange(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        Exchange exchange = repository.findByFromAndTo(from, to);

        if (exchange == null) throw new RuntimeException("Currency unsupported!");

        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        exchange.setConvertedValue(convertedValue);
        exchange.setEnvironment(informationService.retrieveServerPort());

        return ResponseEntity.ok().body(exchange);
    }
}
