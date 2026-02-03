package edu.ucr.c24249.library.view;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

import edu.ucr.c24249.library.controller.RegisterProfessorController;

import java.util.Scanner;

public class RegisterProfessorView {
    private final RegisterProfessorController controller;

    public RegisterProfessorView(RegisterProfessorController controller) {
        this.controller = controller;
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======= Registro de Profesores =======");
        String cardId = capture(scanner, "Identificación");
        String fullName = capture(scanner, "Nombre Completo");
        String address = capture(scanner, "Dirección");

        RegisterProfessorController.Result result = controller.register(cardId, fullName, address);

        switch (result) {
            case CARD_ID_NULL:
                System.out.println("La identificación es incorrecta");
                break;
            case FULL_NAME_NULL:
                System.out.println("EL Nombre es incorrecta");
                break;
            case ADDRESS_NULL:
                System.out.println("La dirección es incorrecta");
                break;
            case DUPLICATE_CARD_ID:
                System.out.println("La identificación ya existe");
                break;
            default:
                System.out.println("Estudiante registrado existosamente!");
                System.out.println();
                break;
        }
    }

    // Método para capturar datos de la consola
    private String capture(Scanner scanner, String label) {
        System.out.println("Ingrése " + label + ": ");
        String value = scanner.nextLine();
        System.out.println();
        return value;
    }
}
