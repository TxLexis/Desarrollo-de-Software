package edu.ucr.c24249.library.view;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

import edu.ucr.c24249.library.controller.CreateLoanController;

import java.util.Scanner;

public class CreateLoanView {

    private final CreateLoanController loanController;

    public CreateLoanView(CreateLoanController loanController) {
        this.loanController = loanController;
    }

    public void createLoan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======= Create Loan =======");

        // Solicitar datos del usuario
        String isbn = capture(scanner, "ISBN");
        String memberCardId = capture(scanner, "Identificación del Miembro");

        // Llamar al controlador
        CreateLoanController.Result result = loanController.createLoan(isbn, memberCardId);

        // Manejar el resultado
        switch (result) {
            case BOOK_NOT_FOUND:
                System.out.println("Libro no encontrado");
                break;
            case BOOK_NOT_AVAILABLE:
                System.out.println("El Libro no está disponible");
                break;
            case MEMBER_NOT_FOUND:
                System.out.println("Miembro no encontrado");
                break;
            default:
                System.out.println("El prestamo se hizo correctamente!");
                System.out.println();
                break;
        }
    }

    // Método para capturar entradas del usuario desde la consola con un mensaje personalizado
    private String capture(Scanner scanner, String label) {
        System.out.println("Ingrése " + label + ": ");
        String value = scanner.nextLine();
        System.out.println();
        return value;
    }
}
