package edu.ucr.c24249.library.repository;

import edu.ucr.c24249.library.model.Book;
import edu.ucr.c24249.library.model.Member;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class BookRepository {
    private Book[] books;
    private int saveIndex;


    public BookRepository() {
        books = new Book[10];
    }

    //save
    public void save(Book book){
        books[saveIndex] =  book;
        saveIndex++;
    }

    public boolean add(Book book){
        // false si está lleno
        if(book == null || saveIndex >= books.length){
            return false;
        }

        //Agrega un nuevo miembro
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
                return books[i];
            }
        }
        return null;
    }

    public Book[] findAll(){
        // retorna copia con size elementos (no todo el arreglo)
        Book[] copyBooks = new Book[saveIndex];
        for (int i = 0; i < saveIndex; i++) {
            copyBooks[i] = books[i];
        }
        return copyBooks;
    }

}
