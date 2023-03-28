package SmoothSortLinkedList;

import java.util.LinkedList;

public class SmoothSort {

    public static LinkedList<Integer> LeonardoNumbers;

    public static int iterations = 0;

    public static int sort (LinkedList<Integer> arr) {

        int arrLength = arr.size(); // длина массива

        LeonardoNumbersGenerator(arrLength); // вычисление необходимого числа чисел Леонардо

        LinkedList<Integer> heap = new LinkedList<>(); // куча в виде списка деревьев Леонардо (с запасом)

        int size;

        for (int i = 0; i < arrLength; i++) {
            size = heap.size();
            iterations++;
            if (size >= 2 && heap.get(size - 2) == heap.get(size - 1) + 1) {
                heap.removeLast();
                size--;
                heap.set(size - 1, heap.getLast() + 1);
            } else {
                if (size >= 1 && heap.getLast() == 1) {
                    heap.addLast(0);
                } else {
                    heap.addLast(1);
                }
            }
            restoreHeap(arr, i, heap, heap.size());
        }

        // Анализируем кучу из куч
        for (int i = arrLength - 1; i > 0; i--) { // идём с конца, чтобы брать дочерние элементы из корней
            iterations++;
            if (heap.getLast() < 2) {
                heap.removeLast();
            } else {
                int k = heap.getLast();
                heap.removeLast();
                LinkedList<Integer> childTrees = getChildTrees(i, k);
                int tr = childTrees.removeFirst();
                int kr = childTrees.removeFirst();
                int tl = childTrees.removeFirst();
                int kl = childTrees.removeFirst();
                heap.addLast(kl);
                restoreHeap(arr, tl, heap, heap.size());
                heap.addLast(kr);
                restoreHeap(arr, tr, heap, heap.size());
            }
        }
        System.out.println(arr);
        return iterations;
    }


    // Восстановление кучи после слияния куч или удаления корня
    public static void restoreHeap (LinkedList<Integer> arr, int i, LinkedList<Integer> heap, int capacity) {

        // Модифицированная сортировка вставками для корней куч

        int current = capacity - 1;
        int k = heap.get(current);
        int j;
        while (current > 0) {
            iterations++;
            j = i - LeonardoNumbers.get(k);
            if (arr.get(j) > arr.get(i) &&
                    (k < 2 || arr.get(j) > arr.get(i - 1) && arr.get(j) > arr.get(i - 2))) {
                swap(arr, i, j);
                i = j;
                current -= 1;
                k = heap.get(current);
            } else {
                break;
            }
        }

        // Просейка

        while (k >= 2) {
            iterations++;
            LinkedList<Integer> childTrees = getChildTrees(i, k);
            int tr = childTrees.removeFirst();
            int kr = childTrees.removeFirst();
            int tl = childTrees.removeFirst();
            int kl = childTrees.removeFirst();
            if (arr.get(i) < arr.get(tr) || arr.get(i) < arr.get(tl)) {

                if (arr.get(tr) > arr.get(tl)) {
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
    public static LinkedList<Integer> getChildTrees (int i, int k) {
        int tr = i - 1;
        int kr = k - 2;
        int tl = tr - LeonardoNumbers.get(kr);
        int kl = k - 1;
        LinkedList<Integer> childTrees = new LinkedList<>();
        childTrees.add(tr);
        childTrees.add(kr);
        childTrees.add(tl);
        childTrees.add(kl);
        return childTrees;
    }

    // Генерация чисел Леонардо, не превышающих количество элементов массива
    public static void LeonardoNumbersGenerator (int thresholdValue) {
        LeonardoNumbers = new LinkedList<>();
        int firstNumber = 1;
        int secondNumber = 1;
        int temp;
        while (firstNumber < thresholdValue) {
            iterations++;
            LeonardoNumbers.add(firstNumber);
            temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp + secondNumber + 1;
        }
    }

    // Обмен двух чисел из массива
    public static void swap (LinkedList<Integer> arr, int i, int j) {
        int temp = arr.get(j);
        arr.set(j, arr.get(i));
        arr.set(i, temp);
    }
}