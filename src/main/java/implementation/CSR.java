package implementation;

import data.CompressedSparseRow;
import engine.CSREngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSR implements CompressedSparseRow, CSREngine {
    private BufferedReader br;
    private final int linesToRead = 30622564;

    private int[] ptr;
    private int[] idx;

    /**
     * Builds the CSR by reading information from soc-pokec-relationships.txt
     *
     * @param filepath path of input file
     */
    public void buildFromFile(String filepath) {
        // Open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        readInput(linesToRead);

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();

        if(vertex_id >= ptr.length || vertex_id <= 0)
            return null;

        for(int j = 0; (vertex_id < ptr.length-1 && j < (ptr[vertex_id] - ptr[vertex_id-1])) || (vertex_id == ptr.length-1 && j < idx.length-1-ptr[vertex_id-1]); j++) {
            //System.out.println(vertex_id+" "+idx[ptr[vertex_id-1]+j]);
            neighbors.add(idx[ptr[vertex_id-1]+j]);
        }

        return neighbors;
    }

    private void readInput(int linesToRead) {
        ArrayList<Integer> ptr = new ArrayList<Integer>();
        ArrayList<Integer> idx = new ArrayList<Integer>();

        String line;
        String[] lineSplitted = null;

        int oldLineId = 0;

        for (int i = 0; i < linesToRead; i++) {
            try {
                line = br.readLine();
                if (line == null)
                    break;

                lineSplitted = line.split("\t");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            int n, m;
            try {
                n = Integer.parseInt(lineSplitted[0]);
                m = Integer.parseInt(lineSplitted[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                break;
            }

            // Aggiunge un nodo di partenza nella lista dei pointer
            if(oldLineId != n) {
                for(int k=(oldLineId+1); k < n && oldLineId != 0; k++){
                    //System.out.println("Index: "+k+", num: "+n+", old: "+oldLineId);
                    ptr.add(idx.size());
                }

                ptr.add(idx.size());
                oldLineId = n;
            }

            // Add out-neighbour into idx array
            idx.add(m);
        }

        this.ptr = ArrayListIntegerToInt(ptr);
        this.idx = ArrayListIntegerToInt(idx);
    }

    private int[] ArrayListIntegerToInt(ArrayList<Integer> integerArrayList){
        int[] intArray = new int[integerArrayList.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = integerArrayList.get(i);
        }
        return intArray;
    }

    @Override
    public ArrayList<Integer> traverse(CompressedSparseRow csr, Integer vertex_id) {
        return null;
    }
}
