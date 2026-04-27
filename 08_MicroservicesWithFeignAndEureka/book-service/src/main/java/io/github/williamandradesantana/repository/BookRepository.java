package io.github.williamandradesantana.repository;

import io.github.williamandradesantana.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
