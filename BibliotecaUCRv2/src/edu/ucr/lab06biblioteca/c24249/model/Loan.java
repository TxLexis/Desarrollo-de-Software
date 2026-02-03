package edu.ucr.lab06biblioteca.c24249.model;

import java.time.LocalDateTime;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class Loan {
    private final Member member;
    private final Book book;
    private final LocalDateTime initialDate;
    private LocalDateTime dueDate;

    public Loan(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.initialDate = LocalDateTime.now();
        book.markAsLoaned();
    }

    // Constructor copia
    public Loan(Loan other) {
        this.member = other.member.copy();
        this.book = other.book.copy();
        this.initialDate = other.initialDate;
        this.dueDate = other.dueDate;
    }

    //Genera una copia de Loan con los mismos valores
    public Loan copy() {
        return new Loan(this);
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void close(LocalDateTime closeDate){
        book.markAsAvailable();
        this.dueDate=closeDate;
    }
}
