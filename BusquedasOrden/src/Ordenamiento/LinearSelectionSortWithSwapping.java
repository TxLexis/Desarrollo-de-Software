package Ordenamiento;

public class LinearSelectionSortWithSwapping<T extends Comparable<T>> {

    public void linearSelectionSort(T[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Buscar el índice del mínimo
            for (int j = i + 1; j < n; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Realizar el intercambio inmediatamente
            T temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    public static void main(String[] args) {
        LinearSelectionSortWithSwapping<String> selectionSort =
                new LinearSelectionSortWithSwapping<>();

        String[] stringArray = {"banana", "apple", "orange", "grape", "kiwi"};

        System.out.println("Arreglo original:");
        for (String str : stringArray) {
            System.out.print(str + " ");
        }

        selectionSort.linearSelectionSort(stringArray);

        System.out.println("\nArreglo ordenado:");
        for (String str : stringArray) {
            System.out.print(str + " ");
        }
    }
}