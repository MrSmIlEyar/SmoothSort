import SmoothSortArray.SmoothSort;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) throws IOException {
        String fileName = "data.txt";
        List<List<Integer>> numberLists = readNumberListsFromFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("statistics1.txt"))) {
            int[] arr;
            for (List<Integer> listInt: numberLists) {
                arr = new int[listInt.size()];
                for (int i = 0; i < listInt.size(); i++) {
                    arr[i] = listInt.get(i);
                }
                long startTime = System.nanoTime();
                int iterations = SmoothSort.sort(arr);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                writer.write(listInt.size() + ";" + elapsedTime + ";" + iterations + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error generating random number file: " + e.getMessage());
        }
    }

    public static List<List<Integer>> readNumberListsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading number lists from file: " + e.getMessage());
            return null;
        }
    }

}