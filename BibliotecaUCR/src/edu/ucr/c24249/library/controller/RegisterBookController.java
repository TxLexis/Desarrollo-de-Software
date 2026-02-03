package edu.ucr.c24249.library.controller;

import edu.ucr.c24249.library.model.Book;
import edu.ucr.c24249.library.repository.BookRepository;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class RegisterBookController {
    private final BookRepository repository;

    public RegisterBookController(BookRepository repository) {
        this.repository = repository;
    }

    public enum Result {OK, ISBN_NULL, AUTHOR_NULL,TITLE_NULL}
    public Result register(String isbn, String autor, String title){
        //validar
        if(isbn==null || isbn.isBlank()) return Result.ISBN_NULL;
        if(autor==null || autor.isBlank()) return Result.AUTHOR_NULL;
        if(title==null || title.isBlank()) return Result.TITLE_NULL;
        Book book = new Book(isbn, title, autor);
        repository.save(book);
        return Result.OK;
    }

}
