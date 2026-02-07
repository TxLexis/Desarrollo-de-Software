package Busquedas;

public class BusquedaLineal {
    public static int busquedaLineal(int[] arr, int objetivo) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == objetivo) {
                return i; // índice encontrado
            }
        }
        return -1; // no encontrado
    }

}
