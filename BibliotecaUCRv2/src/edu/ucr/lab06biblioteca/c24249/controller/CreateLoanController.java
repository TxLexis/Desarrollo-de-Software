package edu.ucr.lab06biblioteca.c24249.controller;

import edu.ucr.lab06biblioteca.c24249.model.Book;
import edu.ucr.lab06biblioteca.c24249.model.Loan;
import edu.ucr.lab06biblioteca.c24249.model.Member;
import edu.ucr.lab06biblioteca.c24249.repository.IBookRepository;
import edu.ucr.lab06biblioteca.c24249.repository.ILoanRepository;
import edu.ucr.lab06biblioteca.c24249.repository.IMemberRepository;

import java.util.Optional;


// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class CreateLoanController {

    private final IBookRepository bookRepository;
    private final IMemberRepository memberRepository;
    private final ILoanRepository loanRepository;

    public CreateLoanController(IBookRepository bookRepository, IMemberRepository memberRepository, ILoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    public enum Result {BOOK_NOT_FOUND, BOOK_NOT_AVAILABLE, MEMBER_NOT_FOUND, LOAN_CREATED}

    public Result createLoan(String isbn, String memberCardId){
        //Libro
        Optional<Book> bookRef = bookRepository.findReferenceByIsbn(isbn);
        if(bookRef==null) return Result.BOOK_NOT_FOUND;

        Book book = bookRef.get();
        if(!book.getStatus().equals("AVAILABLE"))return Result.BOOK_NOT_AVAILABLE;

        //Miembro
        Member member = memberRepository.findByCardId(memberCardId);
        if(member==null) return Result.MEMBER_NOT_FOUND;

        //Crea el prestamo
        Loan loan = new Loan(member.copy(),book);
        loanRepository.add(loan);

        book.markAsLoaned();
        return Result.LOAN_CREATED;
    }
}
