package edu.ucr.c24249.library.model;

import java.time.LocalDateTime;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class Loan {
    private Member member;
    private Book book;
    private LocalDateTime initialDate;
    private LocalDateTime dueDate;

    public Loan(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.initialDate = LocalDateTime.now();
        book.markAsLoaned();
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
