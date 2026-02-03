package edu.ucr.c24249.library.model;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR


public class Book {
    private String isbn; //Identificador
    private String title; //Nombre del libro
    private String autor; //Nombre del autor
    private String status; //Estado del libro

    public Book(String isbn, String title, String autor) {
        //TODO validar nulabilidad
        this.isbn = isbn;
        this.title = title;
        this.autor = autor;
        this.status = "AVAILABLE";
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
