package io.github.williamandradesantana.repository;

import io.github.williamandradesantana.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Exchange findByFromAndTo(String from, String to);
}
