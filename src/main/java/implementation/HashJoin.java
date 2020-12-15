package implementation;

import data.Table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class HashJoin implements Table {

    private HashMap<Integer, LinkedList<Integer>> hashMap;
    private BufferedReader br;
    private int maxLinesToRead = 30622564;
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
        int n, m, prevN = -1;
        LinkedList<Integer> prevNNeighbors = null;
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

            // System.out.println("relation " + i + ": " + n + " -> " + m);

            //idea: salvare n precedente per avere confronto senza fare containsKey
            if(n == prevN) {
                prevNNeighbors.add(m);
            } else if (hashMap.containsKey(n)) {
                prevNNeighbors = hashMap.get(n);
                prevNNeighbors.add(m);
            } else {
                LinkedList<Integer> neighbors = new LinkedList<>();
                neighbors.add(m);
                hashMap.put(n, neighbors);
                prevNNeighbors = neighbors;
            }

            prevN = n;
        }
    }

    public LinkedList<Integer> getNeighbors(Integer vertex_id) {
        return hashMap.get(vertex_id);
    }

}
