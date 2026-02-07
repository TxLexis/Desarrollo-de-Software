package Ordenamiento;

public class QuickSort {

    /**
     * Método principal de Quick Sort
     *
     * @param array arreglo a ordenar
     * @param low índice inicial
     * @param high índice final
     */
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Encontrar el índice de partición
            int partitionIndex = partition(array, low, high);

            // Ordenar recursivamente los elementos antes y después de la partición
            quickSort(array, low, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, high);
        }
    }

    /**
     * Particiona el arreglo alrededor del pivote
     *
     * @param array arreglo a particionar
     * @param low índice inicial
     * @param high índice final (pivote)
     * @return índice de la posición final del pivote
     */
    private static int partition(int[] array, int low, int high) {
        // Elegir el último elemento como pivote
        int pivot = array[high];

        // Índice del elemento más pequeño
        int i = low - 1;

        // Recorrer el arreglo y reorganizar elementos
        for (int j = low; j < high; j++) {
            // Si el elemento actual es menor o igual al pivote
            if (array[j] <= pivot) {
                i++;
                // Intercambiar array[i] y array[j]
                swap(array, i, j);
            }
        }

        // Colocar el pivote en su posición correcta
        swap(array, i + 1, high);

        return i + 1;
    }

    /**
     * Intercambia dos elementos en el arreglo
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Método auxiliar para iniciar el ordenamiento
    public static void sort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90, 88, 45, 50};

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