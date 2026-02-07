package Ordenamiento;

public class BubbleSortOptimizado {
    public static void bubbleSortOptimizado(int[] arr) {
        int n = arr.length;
        boolean intercambio;

        for (int i = 0; i < n - 1; i++) {
            intercambio = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambio = true;
                }
            }

            // Si no hubo intercambios, ya está ordenado
            if (!intercambio) break;
        }
    }

}
