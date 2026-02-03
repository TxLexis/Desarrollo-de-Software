package edu.ucr.c24249.library.view;

import edu.ucr.c24249.library.controller.RegisterBookController;

import java.util.Scanner;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class RegisterBookView {
    private RegisterBookController controller;

    public RegisterBookView(RegisterBookController controller) {
        this.controller = controller;
    }

    public void register(){
        Scanner scanner =  new Scanner(System.in);
        System.out.println("======= Registro de Libros =======");
        String isbn = capture(scanner, "ISBN");
        String title = capture(scanner, "Titulo");
        String author = capture(scanner, "Autor");
        var result = controller.register(isbn,title,author);

        switch (result){
            case RegisterBookController.Result.AUTHOR_NULL:
                System.out.println("El Autor es incorrecto");
                break;
            case RegisterBookController.Result.ISBN_NULL:
                System.out.println("El ISBN es incorrecto");
                break;
            case RegisterBookController.Result.TITLE_NULL:
                System.out.println("El Título es incorrecto");
                break;
            default:
                System.out.println("Libro registrado exitosamente!");
                System.out.println();
                break;
        }
    }

    private String capture(Scanner scanner, String label){
        System.out.println("Ingrése " + label + ": ");
        String value = scanner.nextLine();
        System.out.println();
        return value;
    }
}
