package edu.ucr.lab06biblioteca.c24249.view;

import java.util.Scanner;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class MainMenuView {
    private final RegisterProfessorView professorView;
    private final RegisterStudentView studentView;
    private final RegisterBookView bookView;

    private final CreateLoanView loanView;

    public MainMenuView(RegisterStudentView studentView, RegisterProfessorView professorView, RegisterBookView bookView, CreateLoanView loanView) {
        this.professorView = professorView;
        this.studentView = studentView;
        this.bookView = bookView;
        this.loanView = loanView;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Se mantendra hasta que running sea false
        while (running) {
            System.out.println("===== Biblioteca =====");
            System.out.println(" = = = = Menu = = = =");
            System.out.println("1. Registrar Estudiante \n" +
                    "2. Registrar Profesor \n" +
                    "3. Registrar Libros \n" +
                    "4. Registrar Prestamo \n" +
                    "5. Lista de miembros \n" +
                    "6. Lista de libros \n" +
                    "0. Salir ");

            System.out.println("Escoge la opción que deseas: ");
            String option = scanner.nextLine();

            //Switch dependiendo de la opción escogida
            switch (option) {
                case "1":
                    studentView.register();
                    break;
                case "2":
                    professorView.register();
                    break;
                case "3":
                    bookView.register();
                    break;
                case "4":
                    loanView.createLoan();
                    break;
                case "5":
                    System.out.println("Aún no Implementado");
                    break;
                case "6":
                    System.out.println("Aún no Implementado");
                    break;
                case "0":
                    System.out.println("Hasta la proxima!");
                    running = false;
            }
        }

    }
}