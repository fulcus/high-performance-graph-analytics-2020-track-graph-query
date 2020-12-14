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
    private int linesToRead = 30622564;
    private int initNeighborsList = 5;
    private int k = 1; //multiplication factor for hashmap size

    @Override
    public void buildFromFile(String filepath) {
        //open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        hashMap = new HashMap<>(k * linesToRead);
        readInput(linesToRead);
    }

    private void readInput(int linesToRead) {

        String inputString;
        String[] numbers = null;
        int n, m, prevN = -1;
        ArrayList<Integer> prevNNeighbors = null;
        for (int i = 0; i < linesToRead; i++) {

            try {
                inputString = br.readLine();
                if (inputString == null)
                    break;
                numbers = inputString.split("\t");
            } catch (IOException e) {
                e.printStackTrace();
            }

            n = Integer.parseInt(numbers[0]);
            m = Integer.parseInt(numbers[1]);

            System.out.println("relation " + i + ": " + n + " -> " + m);

            //idea: salvare n precedente per avere confronto senza fare containsKey
            if(n == prevN) {
                prevNNeighbors.add(m);
            } else if (hashMap.containsKey(n)) {
                prevNNeighbors = hashMap.get(n);
                prevNNeighbors.add(m);
            } else {
                ArrayList<Integer> neighbors = new ArrayList<>(initNeighborsList);
                neighbors.add(m);
                hashMap.put(n, neighbors);
                prevNNeighbors = neighbors;
            }

            prevN = n;
        }
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        return hashMap.get(vertex_id);
    }

}
