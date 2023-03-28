import SmoothSortLinkedList.SmoothSort;

import java.io.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class test2 {

    public static void main(String[] args) throws IOException {
        String fileName = "data.txt";
        List<List<Integer>> numberLists = readNumberListsFromFile(fileName);
        LinkedList<LinkedList<Integer>> numberLists2 = new LinkedList<>();
        for (List<Integer> l : numberLists) {
            numberLists2.add(new LinkedList<>(l));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("statistics2.txt"))) {
            for (LinkedList<Integer> listInt: numberLists2) {
                long startTime = System.nanoTime();
                int iterations = SmoothSort.sort(listInt);
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