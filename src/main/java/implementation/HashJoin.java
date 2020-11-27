package implementation;

import data.Table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HashJoin implements Table {

    private HashMap<Integer, ArrayList<Integer>> hashMap;
    private BufferedReader br;
    private int linesToRead = 1000;
    private int initList = 5;
    private int batches = 3; //number of blocks of linesToRead to scan
    private int k = 1; //multiplication factor for hashmap size

    public static void main(String[] args) {
        HashJoin hash = new HashJoin();
        hash.buildFromFile("src/main/resources/soc-pokec-relationships.txt");

        for (int i = 1; i <= 3000; i++) {
            System.out.println("Neighbors of " + i + " node!");
            System.out.println(hash.getNeighbors(i));
        }

        HashJoinE hashJoinE = new HashJoinE();

        for (int i = 1; i <= 1; i++) {
            System.out.println("Traverse of " + i + " node!");
            ArrayList<Integer[]> queryResult = hashJoinE.join(hash, i, 2);

            for (Integer[] integers : queryResult) {
                System.out.println(Arrays.toString(integers));
            }
        }

    }

    @Override
    public void buildFromFile(String filepath) {
        //open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        hashMap = new HashMap<>(k * linesToRead * batches);

        for (int i = 0; i < batches; i++) {
            readInput(linesToRead, i);
        }
    }

    private void readInput(int linesToRead, int batchNumber) {

        String inputString;
        String[] numbers = null;

        for (int i = 0; i < linesToRead; i++) {

            try {
                inputString = br.readLine();
                if (inputString == null)
                    break;
                numbers = inputString.split("\t");
            } catch (IOException e) {
                e.printStackTrace();
            }

            int n = Integer.parseInt(numbers[0]);
            int m = Integer.parseInt(numbers[1]);

            System.out.println("relation " + (batchNumber * linesToRead + (i + 1)) + ": " + n + " -> " + m);

            if (hashMap.containsKey(n)) {
                hashMap.get(n).add(m);
            } else {
                ArrayList<Integer> values = new ArrayList<>(initList);
                values.add(m);
                hashMap.put(n, values);
            }
        }
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        return hashMap.get(vertex_id);
    }

}
