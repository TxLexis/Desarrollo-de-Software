package Ordenamiento;

public class SinkingSort {
    public static void sinkingSort(int[] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = i + 1; j < datos.length; j++) {
                if (datos[i] > datos[j]) {
                    int temporal = datos[i];
                    datos[i] = datos[j];
                    datos[j] = temporal;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] datos = {5, 2, 8, 3, 1, 6, 4};

        System.out.println("Arreglo original:");
        imprimirArreglo(datos);

        sinkingSort(datos);

        System.out.println("\nArreglo ordenado:");
        imprimirArreglo(datos);
    }

    private static void imprimirArreglo(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
