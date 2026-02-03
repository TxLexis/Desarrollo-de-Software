package edu.ucr.lab06biblioteca.c24249.controller;

import edu.ucr.lab06biblioteca.c24249.model.Book;
import edu.ucr.lab06biblioteca.c24249.repository.IBookRepository;


// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class RegisterBookController {
    private final IBookRepository repository;

    public RegisterBookController(IBookRepository repository) {
        this.repository = repository;
    }

    public enum Result {OK, ISBN_NULL, AUTHOR_NULL,TITLE_NULL}
    public Result register(String isbn, String autor, String title){
        //validar
        if(isbn==null || isbn.isBlank()) return Result.ISBN_NULL;
        if(autor==null || autor.isBlank()) return Result.AUTHOR_NULL;
        if(title==null || title.isBlank()) return Result.TITLE_NULL;

        Book book = new Book(isbn, title, autor);
        repository.add(book);
        return Result.OK;
    }

}
