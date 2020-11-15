package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSR implements CompressedSparseRow {
    private int[] ptr;
    private int[] idx;

    public static void main(String[] args) {
        CSR csr = new CSR();
        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");
    }

    public CSR(){
        ptr = new int[1000];
        idx = new int[1000];
    }

    /**
     * Builds the CSR by reading information from soc-pokec-relationships.txt
     *
     * @param filepath path of input file
     */
    public void buildFromFile(String filepath) {
        readInput(filepath, 1000);
    }

    public ArrayList<Integer> getNeighbors(Integer vertex_id) {
        return null;
    }

    private void readInput(String filepath, int linesToRead) {
        //open file
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String inputString;
        String[] numbers = null;

        int i_ptr = 0, i_idx = 0;
        for (int i = 0; i < linesToRead; i++) {
            try {
                inputString = br.readLine();
                if (inputString == null)
                    break;
                numbers = inputString.split("\t");

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(numbers[0]);
            int n = Integer.parseInt(numbers[0]);
            int m = Integer.parseInt(numbers[1]);
            System.out.println("Relation " + i + " - " + n + " " + m);

            // Aggiunge un nodo di partenza nella lista dei pointer
            if(i_ptr != n){
                ptr[n-1] = i_idx;
                i_ptr = n;
            }

            // Aggiunge nodo di arrivo del collegamneto
            idx[i_idx++] = m;
        }

        System.out.println("______________________________");
        for(int i = 0; i < i_ptr-1; i++){
            for(int j = 0; j < (ptr[i+1] - ptr[i]); j++){
                System.out.println((i+1)+" "+idx[ptr[i]+j]);
            }
        }
    }

}
