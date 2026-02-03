package edu.ucr.lab06biblioteca.c24249;

import edu.ucr.lab06biblioteca.c24249.controller.CreateLoanController;
import edu.ucr.lab06biblioteca.c24249.controller.RegisterBookController;
import edu.ucr.lab06biblioteca.c24249.controller.RegisterProfessorController;
import edu.ucr.lab06biblioteca.c24249.controller.RegisterStudentController;
import edu.ucr.lab06biblioteca.c24249.repository.*;
import edu.ucr.lab06biblioteca.c24249.view.*;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class App {
    public static void main(String[] args) {
        //Repositorios
        IBookRepository bookRepository = new BookRepository();
        IMemberRepository memberRepository = new MemberRepository(100);
        ILoanRepository loanRepository = new LoanRepository();

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
