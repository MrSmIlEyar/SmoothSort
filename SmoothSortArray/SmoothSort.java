package SmoothSortArray;

public class SmoothSort {

    public static int[] LeonardoNumbers;
    public static int iterations = 0;

    public static int sort (int[] arr) {

        int arrLength = arr.length; // длина массива

        LeonardoNumbersGenerator(arrLength); // вычисление необходимого числа чисел Леонардо

        int[] heap = new int[arrLength]; // куча в виде массива деревьев Леонардо (с запасом)

        int capacity = 0; // фактический размер кучи

        for (int i = 0; i < arrLength; i++) {
            iterations++;
            if (capacity >= 2 && heap[capacity - 2] == heap[capacity - 1] + 1) {
                heap[--capacity] = 0;
                heap[capacity - 1] += 1;
            } else {
                if (capacity >= 1 && heap[capacity - 1] == 1) {
                    capacity++;
                } else {
                    heap[capacity++] = 1;
                }
            }
            restoreHeap(arr, i, heap, capacity);
        }

        // Анализируем кучу из куч
        for (int i = arrLength - 1; i > 0; i--) { // идём с конца, чтобы брать дочерние элементы из корней
            iterations++;
            if (heap[capacity - 1] < 2) {
                heap[--capacity] = 0;
            } else {
                int k = heap[capacity - 1];
                heap[--capacity] = 0;
                int[] childTrees = getChildTrees(i, k);
                int tr = childTrees[0];
                int kr = childTrees[1];
                int tl = childTrees[2];
                int kl = childTrees[3];
                heap[capacity++] = kl;
                restoreHeap(arr, tl, heap, capacity);
                heap[capacity++] = kr;
                restoreHeap(arr, tr, heap, capacity);
            }
        }
        for (int k : arr) {
            System.out.print(k + " ");
        }
        return iterations;
    }


    // Восстановление кучи после слияния куч или удаления корня
    public static void restoreHeap (int[] arr, int i, int[] heap, int capacity) {

        // Модифицированная сортировка вставками для корней куч

        int current = capacity - 1;
        int k = heap[current];
        int j;
        while (current > 0) {
            iterations++;
            j = i - LeonardoNumbers[k];
            if (arr[j] > arr[i] &&
                    (k < 2 || arr[j] > arr[i - 1] && arr[j] > arr[i - 2])) {
                swap(arr, i, j);
                i = j;
                current -= 1;
                k = heap[current];
            } else {
                break;
            }
        }

        // Просейка

        while (k >= 2) {
            iterations++;
            int[] childTrees = getChildTrees(i, k);
            int tr = childTrees[0];
            int kr = childTrees[1];
            int tl = childTrees[2];
            int kl = childTrees[3];
            if (arr[i] < arr[tr] || arr[i] < arr[tl]) {

                if (arr[tr] > arr[tl]) {
                    swap(arr, i, tr);
                    i = tr;
                    k = kr;
                }

                else {
                    swap(arr, i, tl);
                    i = tl;
                    k = kl;
                }
            } else {
                break;
            }
        }
    }

    // два предыдущих числа Леонардо
    public static int[] getChildTrees (int i, int k) {
        int tr = i - 1;
        int kr = k - 2;
        int tl = tr - LeonardoNumbers[kr];
        int kl = k - 1;
        return new int[] {tr, kr, tl, kl};
    }

    // Генерация чисел Леонардо, не превышающих количество элементов массива
    public static void LeonardoNumbersGenerator (int thresholdValue) {
        LeonardoNumbers = new int[thresholdValue];
        int firstNumber = 1;
        int secondNumber = 1;
        int temp;
        int i = 0;
        while (firstNumber < thresholdValue) {
            iterations++;
            LeonardoNumbers[i] = firstNumber;
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp + secondNumber + 1;
            i++;
        }
    }

    // Обмен двух чисел из массива
    public static void swap (int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
