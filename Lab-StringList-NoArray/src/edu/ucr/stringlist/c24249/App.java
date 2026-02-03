package edu.ucr.stringlist.c24249;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        //Crear una instancia de lista
        StringList list = new LinkedStringList();

        //Se necesita generar Strings aleatorios
        Random rand = new Random();
        while (list.size() < 10_000) {
            String value = String.valueOf(rand.nextInt(20_000));
            if(!list.exists(value)){
                list.add(value);
            }
        }

        //Impresiones
        System.out.println("Final size: " + list.size());
        System.out.println("List preview: " + list.toString().substring(0, 200) + "...");

        //Prueba de remove
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(rand.nextInt(20_000));
            list.remove(value);
        }
        System.out.println("Size after removals: " + list.size());
    }
}
