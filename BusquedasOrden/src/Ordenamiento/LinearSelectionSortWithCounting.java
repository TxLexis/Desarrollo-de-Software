package Ordenamiento;

public class LinearSelectionSortWithCounting {

        public static void linearSelectionSort(int[] array) {
            int n = array.length;

            for (int i = 0; i < n - 1; i++) {
                // Encontrar el índice del elemento más pequeño
                int minIndex = i;

                for (int j = i + 1; j < n; j++) {
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }

                // Intercambiar el elemento más pequeño con el primero
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }

        public static void main(String[] args) {
            int[] intArray = {64, 34, 25, 12, 22, 11, 90};

            System.out.println("Arreglo original:");
            imprimirArreglo(intArray);

            linearSelectionSort(intArray);

            System.out.println("\nArreglo ordenado:");
            imprimirArreglo(intArray);
        }

        private static void imprimirArreglo(int[] arr) {
            for (int num : arr) {
                System.out.print(num + " ");
            }
        }

}
