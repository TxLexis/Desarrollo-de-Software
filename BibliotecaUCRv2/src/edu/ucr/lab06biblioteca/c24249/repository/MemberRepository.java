package edu.ucr.lab06biblioteca.c24249.repository;

import edu.ucr.lab06biblioteca.c24249.model.Member;

import java.util.ArrayList;
import java.util.List;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class MemberRepository implements IMemberRepository{

    private Member[] data;
    private int currentSize = 0;

    //Constructor
    public MemberRepository(int capacity) {
        this.data = new Member[capacity];
    }

    public boolean add(Member member){
        // false si está lleno
        if(member == null || currentSize >= data.length){
            return false;
        }

        //Agrega un nuevo libro
        data[currentSize++] = member.copy();
        currentSize++;
        return true;
    }
    public Member findByCardId(String cardId) {
        // null si no existe
        if (cardId == null || cardId.isBlank()){
            return null;
        }

        // Metodo de busqueda
        for (int i = 0; i < currentSize; i++) {
            if(data[i].getCardId().equals(cardId)){
                return data[i].copy();
            }
        }
        return null;
    }

    public List<Member> findAll() {
        // retorna copia con size elementos (no todo el arreglo)
        List <Member> copyMembers = new ArrayList<>();
        for (int i = 0; i < currentSize; i++) {
            copyMembers.add(data[i].copy());
        }
        return copyMembers;
    }
    public int size(){
        return currentSize;

    }


}
