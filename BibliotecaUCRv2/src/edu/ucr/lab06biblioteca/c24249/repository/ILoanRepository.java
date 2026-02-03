package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Loan;

public interface ILoanRepository {
    boolean add(Loan loan);
    java.util.List<Loan> findAll();          // DEEP COPY list
    int size();
}
