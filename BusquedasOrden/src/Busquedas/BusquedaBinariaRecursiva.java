package Busquedas;

public class BusquedaBinariaRecursiva {
    static int busquedaBinariaRecursiva(int[] arreglo, int izquierda, int derecha, int valorBuscado) {
        if (izquierda > derecha) return -1;

        int medio = izquierda + (derecha - izquierda) / 2;

        if (arreglo[medio] == valorBuscado) return medio;

        if (arreglo[medio] < valorBuscado) {
            return busquedaBinariaRecursiva(arreglo, medio + 1, derecha, valorBuscado);
        } else {
            return busquedaBinariaRecursiva(arreglo, izquierda, medio - 1, valorBuscado);
        }
    }
}
