package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSR implements CompressedSparseRow {
    private ArrayList<CSRBlock> blocks;
    private BufferedReader br;
    private final int linesToRead = 30622564;

    private int[] ptr;
    private int[] idx;

    public CSR(){
        blocks = new ArrayList<CSRBlock>();
    }

    /**
     * Builds the CSR by reading information from soc-pokec-relationships.txt
     *
     * @param filepath path of input file
     */
    /*public void buildFromFile(String filepath) {
        // Open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0; i<100; i++) {
            blocks.add(readInput(linesToRead));
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("blocks: "+blocks.size());
    }*/

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

    /* public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();

        for(int i=0; i<blocks.size(); i++){
            CSRBlock x = blocks.get(i);
            System.out.println("L: "+x.getPtr().length);
            for(int j=0; j<x.getPtr().length; j++){
                for(int k = 0; k < (x.getPtr()[j+1] - x.getPtr()[j]); k++){
                    System.out.println((x.getPtr_base()+j)+" "+x.getIdx()[x.getPtr()[j]+k]);
                }
            }
        }

        return neighbors;
    } */

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

    /*private CSRBlock readInput(int startFrom, int linesToRead){
        int[] ptr = new int[linesToRead];
        int[] idx = new int[linesToRead];

        String inputString;
        String[] numbers = null;

        int i_ptr = -1, i_idx = 0, ptr_base = -1;
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
            //System.out.println("Relation " + i + " - " + n + " " + m);

            if(ptr_base == -1)
                ptr_base = n;

            // Aggiunge un nodo di partenza nella lista dei pointer
            if(i_ptr != (n-ptr_base)){
                for(int k=(i_ptr+1); k < (n-ptr_base) && i_ptr != 0; k++){
                    System.out.println("Index: "+k+", num: "+n);
                    ptr[k] = i_idx;
                }
                i_ptr = n-ptr_base;
                ptr[i_ptr] = i_idx;
            }

            // Add out-neighbour into idx array
            idx[i_idx++] = m;
        }

        System.out.println("______________________________");
        for(int i = 0; i < i_ptr-1; i++){
            System.out.println("TEST: "+ptr[i+1]+" "+ptr[i]);
            for(int j = 0; j < (ptr[i+1] - ptr[i]); j++){
                System.out.println((ptr_base+i)+" "+idx[ptr[i]+j]);
            }
        }

        return new CSRBlock(ptr_base, ptr, idx);
    }*/

    private int[] ArrayListIntegerToInt(ArrayList<Integer> integerArrayList){
        int[] intArray = new int[integerArrayList.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = integerArrayList.get(i);
        }
        return intArray;
    }

}
