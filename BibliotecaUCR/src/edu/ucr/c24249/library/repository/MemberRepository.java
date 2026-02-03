package edu.ucr.c24249.library.repository;

import edu.ucr.c24249.library.model.Member;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class MemberRepository {

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
        data[currentSize++] = member;
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
                return data[i];
            }
        }
        return null;
    }

    public Member[] findAll() {
        // retorna copia con size elementos (no todo el arreglo)
        Member[] copyMembers = new Member[currentSize];
        for (int i = 0; i < currentSize; i++) {
            copyMembers[i] = data[i];
        }
        return copyMembers;
    }
    public int size(){
        return currentSize;

    }


}
