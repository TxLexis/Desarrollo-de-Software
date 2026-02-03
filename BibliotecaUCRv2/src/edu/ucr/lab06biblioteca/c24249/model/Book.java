package edu.ucr.lab06biblioteca.c24249.model;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR


public class Book {
    private final String isbn; //Identificador
    private final String title; //Nombre del libro
    private final String autor; //Nombre del autor
    private String status; //Estado del libro

    public Book(String isbn, String title, String autor) {
        //TODO validar nulabilidad
        this.isbn = isbn;
        this.title = title;
        this.autor = autor;
        this.status = "AVAILABLE";
    }

    //Constructor copia
    public Book(Book other) {
        this.isbn = other.isbn;
        this.title = other.title;
        this.autor = other.autor;
        this.status = other.status;
    }

    //Genera una copia de Book con los mismos valores
    public Book copy() {
        return new Book(this);
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void markAsLoaned() {
        this.status="LOANED";
    }

    public void markAsAvailable() {
        this.status="AVAILABLE";
    }
}
