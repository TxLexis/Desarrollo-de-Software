package edu.ucr.c24249.library.model;

// Carnet: C24249
// Nombre: Arbey Alexander León Machado
// Curso: IF0004 - Desarrollo de Software II
// Laboratorio 05: Biblioteca UCR

public class Member {
    private String cardId;
    private String address;
    private String fullName;

    public Member(String cardId, String address, String fullName) {
        this.cardId = cardId;
        this.address = address;
        this.fullName = fullName;
    }

    public String getCardId() {
        return cardId;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
