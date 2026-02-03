package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR


public class BookRepository implements IBookRepository{
    private final Book[] books;
    private int saveIndex;


    public BookRepository() {
        books = new Book[10];
    }

    //save
    public boolean add(Book book){
        // false si está lleno
        if(book == null || saveIndex >= books.length){
            return false;
        }

        for (int i = 0; i < saveIndex; i++) {
            if (books[i].getIsbn().equals(book.getIsbn())) {
                return false;
            }
        }

        //Crear copia del libro y la agrega
        books[saveIndex] = book.copy();
        books[saveIndex++] = book;
        return true;
    }

    public Book findByIsbn(String isbn) {
        // null si no existe
        if (isbn == null || isbn.isBlank()){
            return null;
        }

        // Metodo de busqueda
        for (int i = 0; i < saveIndex; i++) {
            if(books[i].getIsbn().equals(isbn)){
                return books[i].copy();
            }
        }
        return null;
    }

    public List<Book> findAll(){
        //Lista para almacenar copias profundas en Book
        List<Book> copies = new ArrayList<>();
        for (int i = 0; i < saveIndex; i++) {
            copies.add(books[i].copy());
        }
        return copies;
    }

    @Override
    public Optional<Book> findReferenceByIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return Optional.empty();
        }

        // Buscar el libro con referencia
        for (int i = 0; i < saveIndex; i++) {
            if (books[i].getIsbn().equals(isbn)) {
                return Optional.of(books[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public int size() {
        return saveIndex;
    }

}
