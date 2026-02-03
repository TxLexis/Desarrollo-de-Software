package edu.ucr.c24249.library.repository;

import edu.ucr.c24249.library.model.Loan;
import edu.ucr.c24249.library.model.Member;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class LoanRepository {
    private Loan[] loans;
    private int currentSize;

    public LoanRepository() {
        this.loans = new Loan[10];
        this.currentSize = 0;
    }

    public boolean add(Loan loan){
        // false si está lleno
        if(loan == null || currentSize >= loans.length){
            return false;
        }

        //Agrega un nuevo prestamo
        loans[currentSize++] = loan;
        return true;
    }

    public Loan[] findAll(){
        // retorna copia con size elementos (no todo el arreglo)
        Loan[] copyloans = new Loan[currentSize];
        for (int i = 0; i < currentSize; i++) {
            copyloans[i] = loans[i];
        }
        return copyloans;
    }

    public int size(){
        return currentSize;
    }
}
