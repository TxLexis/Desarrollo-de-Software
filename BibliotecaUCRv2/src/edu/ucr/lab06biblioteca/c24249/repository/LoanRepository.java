package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Loan;

import java.util.ArrayList;
import java.util.List;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class LoanRepository implements ILoanRepository{
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
        loans[currentSize++] = loan.copy();
        currentSize++;
        return true;
    }


    public List<Loan> findAll(){
        //Lista para almacenar copias profundas en Loan
        List<Loan> copyloans = new ArrayList<>();
        for (int i = 0; i < currentSize; i++) {
            copyloans.add(loans[i].copy());
        }
        return copyloans;
    }

    public int size(){
        return currentSize;
    }
}
