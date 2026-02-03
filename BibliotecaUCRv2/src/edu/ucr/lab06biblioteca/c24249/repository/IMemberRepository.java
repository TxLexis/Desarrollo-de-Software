package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Member;

public interface IMemberRepository {
    boolean add(Member member);
    Member findByCardId(String cardId);      // returns COPY or null
    java.util.List<Member> findAll();        // DEEP COPY list
    int size();
}
