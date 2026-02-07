package Ordenamiento;

public class MergeSort {

    /**
     * Método principal de Merge Sort
     *
     * @param array arreglo a ordenar
     * @param left índice izquierdo
     * @param right índice derecho
     */
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // Encontrar el punto medio
            int middle = left + (right - left) / 2;

            // Ordenar la primera mitad
            mergeSort(array, left, middle);

            // Ordenar la segunda mitad
            mergeSort(array, middle + 1, right);

            // Combinar las dos mitades ordenadas
            merge(array, left, middle, right);
        }
    }

    /**
     * Combina dos subarreglos ordenados
     *
     * @param array arreglo completo
     * @param left índice inicial del primer subarreglo
     * @param middle índice final del primer subarreglo
     * @param right índice final del segundo subarreglo
     */
    private static void merge(int[] array, int left, int middle, int right) {
        // Tamaños de los dos subarreglos a combinar
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Crear arreglos temporales
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copiar datos a los arreglos temporales
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
        }

        // Índices iniciales de los subarreglos
        int i = 0; // Índice del primer subarreglo
        int j = 0; // Índice del segundo subarreglo
        int k = left; // Índice del arreglo combinado

        // Combinar los arreglos temporales de vuelta en array[left..right]
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copiar los elementos restantes de leftArray[], si hay alguno
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Copiar los elementos restantes de rightArray[], si hay alguno
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Método auxiliar para iniciar el ordenamiento
    public static void sort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        mergeSort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("Arreglo original:");
        imprimirArreglo(array);

        sort(array);

        System.out.println("\nArreglo ordenado:");
        imprimirArreglo(array);
    }

    private static void imprimirArreglo(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}