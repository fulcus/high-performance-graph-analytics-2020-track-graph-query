package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashJoin implements Table {

    private HashMap<Integer, List<Integer>> hashMap;
    private BufferedReader br;
    private int linesToRead = 1000;
    private int initList = 5;
    private int batches = 3; //number of blocks of linesToRead to scan
    private int k = 1; //multiplication factor for hashmap size

    public static void main(String[] args) {
        HashJoin hash = new HashJoin();
        hash.buildFromFile("src/main/resources/soc-pokec-relationships.txt");
    }

    @Override
    public void buildFromFile(String filepath) {
        //open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        hashMap = new HashMap<>(k*linesToRead*batches);

        for (int i = 0; i < batches; i++) {
            readInput(linesToRead);
        }
    }

    private void readInput(int linesToRead) {

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
            System.out.println("batch " + batches + ", relation " + i + ": " + n + " -> " + m);

            if (hashMap.containsKey(n)) {
                hashMap.get(n).add(m);
            } else {
                ArrayList<Integer> values = new ArrayList<>(initList);
                values.add(m);
                hashMap.put(n, values);
            }

        }

    }
}
