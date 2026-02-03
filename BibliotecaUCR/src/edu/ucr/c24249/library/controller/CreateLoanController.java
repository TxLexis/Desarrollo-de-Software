package edu.ucr.c24249.library.controller;

import edu.ucr.c24249.library.model.Book;
import edu.ucr.c24249.library.model.Loan;
import edu.ucr.c24249.library.model.Member;
import edu.ucr.c24249.library.repository.BookRepository;
import edu.ucr.c24249.library.repository.LoanRepository;
import edu.ucr.c24249.library.repository.MemberRepository;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class CreateLoanController {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public CreateLoanController(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    public enum Result {BOOK_NOT_FOUND, BOOK_NOT_AVAILABLE, MEMBER_NOT_FOUND, LOAN_CREATED}

    public Result createLoan(String isbn, String memberCardId){
        //Libro
        Book book  = bookRepository.findByIsbn(isbn);
        if(isbn==null) return Result.BOOK_NOT_FOUND;
        if(!book.getStatus().equals("AVAILABLE"))return Result.BOOK_NOT_AVAILABLE;

        //Miembro
        Member member = memberRepository.findByCardId(memberCardId);
        if(member==null) return Result.MEMBER_NOT_FOUND;

        //Crea el prestamo
        Loan loan = new Loan(member,book);
        loanRepository.add(loan);

        return Result.LOAN_CREATED;
    }
}
