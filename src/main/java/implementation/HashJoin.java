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
    private int maxLinesToRead = 30622564;
    private int initNeighborsList = 5;
    private int k = 1; //multiplication factor for hashmap size

    public void buildFromFile(String filepath, int linesToRead) {
        //open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        hashMap = new HashMap<>(k * linesToRead);
        readInput(linesToRead);
    }

    @Override
    public void buildFromFile(String filepath) {
        buildFromFile(filepath, maxLinesToRead);
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

            // System.out.println("relation " + i + ": " + n + " -> " + m);

            if (hashMap.containsKey(n)) {
                hashMap.get(n).add(m);
            } else {
                ArrayList<Integer> neighbors = new ArrayList<>(initNeighborsList);
                neighbors.add(m);
                hashMap.put(n, neighbors);
            }
        }
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        return hashMap.get(vertex_id);
    }

}
