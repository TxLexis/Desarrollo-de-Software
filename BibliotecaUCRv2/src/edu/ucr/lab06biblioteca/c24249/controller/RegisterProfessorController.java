package edu.ucr.lab06biblioteca.c24249.controller;

import edu.ucr.lab06biblioteca.c24249.model.Student;
import edu.ucr.lab06biblioteca.c24249.repository.IMemberRepository;


// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class RegisterProfessorController {
    private final IMemberRepository repository;

    public RegisterProfessorController(IMemberRepository repository) {
        this.repository = repository;
    }

    public enum Result {OK, CARD_ID_NULL, FULL_NAME_NULL, ADDRESS_NULL, DUPLICATE_CARD_ID}

    public Result register(String cardId, String fullName, String address){
        //Validar
        if(cardId==null || cardId.isBlank()) return Result.CARD_ID_NULL;
        if(fullName==null || fullName.isBlank()) return Result.FULL_NAME_NULL;
        if(address==null || address.isBlank()) return Result.ADDRESS_NULL;
        if(repository.findByCardId(cardId) != null) return Result.DUPLICATE_CARD_ID;

        //Crear y Registrar
        Student student = new Student(cardId,fullName,address);
        repository.add(student);
        return Result.OK;
    }
}
