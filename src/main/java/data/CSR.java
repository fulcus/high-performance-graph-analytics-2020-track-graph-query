package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSR implements CompressedSparseRow {
    private ArrayList<int[]> blocks;
    private BufferedReader br;
    private int linesToRead = 1000;

    public static void main(String[] args) {
        CSR csr = new CSR();
        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");
    }

    public CSR(){
        blocks = new ArrayList<int[]>();
    }

    /**
     * Builds the CSR by reading information from soc-pokec-relationships.txt
     *
     * @param filepath path of input file
     */
    public void buildFromFile(String filepath) {
        //open file
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0; i<2; i++) {
            blocks.add(readInput((i*linesToRead+1), linesToRead));
        }

        System.out.println("blocks: "+blocks.size());
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        return null;
    }

    private int[] readInput(int startFrom, int linesToRead){
        int[] ptr = new int[linesToRead];
        int[] idx = new int[linesToRead];

        String inputString;
        String[] numbers = null;

        int i_ptr = 0, i_idx = 0, ptr_base = -1;
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
            System.out.println("Relation " + i + " - " + n + " " + m);

            if(ptr_base == -1)
                ptr_base = n;

            // Aggiunge un nodo di partenza nella lista dei pointer
            if(i_ptr != n){
                for(int k=(n-ptr_base+1); k < (n-ptr_base) && i_ptr != 0 && (n-ptr_base) <=1000; k++){
                    System.out.println("Index: "+k);
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

        return ptr;
    }

}
