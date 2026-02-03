package edu.ucr.c24249.library;

import edu.ucr.c24249.library.controller.CreateLoanController;
import edu.ucr.c24249.library.controller.RegisterBookController;
import edu.ucr.c24249.library.controller.RegisterProfessorController;
import edu.ucr.c24249.library.controller.RegisterStudentController;
import edu.ucr.c24249.library.repository.BookRepository;
import edu.ucr.c24249.library.repository.LoanRepository;
import edu.ucr.c24249.library.repository.MemberRepository;
import edu.ucr.c24249.library.view.*;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class App {
    public static void main(String[] args) {
        //Repositorios
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository(100);
        LoanRepository loanRepository = new LoanRepository();

        //Controladores
        RegisterBookController bookController = new RegisterBookController(bookRepository);
        RegisterStudentController studentController = new RegisterStudentController(memberRepository);
        RegisterProfessorController profesorController = new RegisterProfessorController(memberRepository);
        CreateLoanController loanController = new CreateLoanController(bookRepository,memberRepository,loanRepository);
        //View
        RegisterBookView bookView =  new RegisterBookView(bookController);
        RegisterStudentView studentView = new RegisterStudentView(studentController);
        RegisterProfessorView professorView = new RegisterProfessorView(profesorController);
        CreateLoanView loanView = new CreateLoanView(loanController);

        //Menu
        MainMenuView mainMenuView = new MainMenuView(studentView, professorView, bookView, loanView);
        mainMenuView.displayMenu();

    }
}
