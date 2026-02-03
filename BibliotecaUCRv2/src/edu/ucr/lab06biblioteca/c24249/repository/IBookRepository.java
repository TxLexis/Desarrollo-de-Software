package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Book;

import java.util.Optional;

public interface IBookRepository {
    boolean add(Book book);
    Book findByIsbn(String isbn);            // returns COPY or null
    Optional<Book> findReferenceByIsbn(String isbn);
    java.util.List<Book> findAll();          // DEEP COPY list
    int size();
}
