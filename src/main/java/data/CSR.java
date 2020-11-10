package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSR implements CompressedSparseRow {

    public static void main(String[] args) {
        CSR csr = new CSR();
        csr.buildFromFile("src/main/resources/soc-test.txt");
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

        for (int i = 0; i < linesToRead; i++) {
            try {
                inputString = br.readLine();
                if (inputString == null)
                    break;
                numbers = inputString.split(" ");

            } catch (IOException e) {
                e.printStackTrace();
            }

            int n = Integer.parseInt(numbers[0]);
            int m = Integer.parseInt(numbers[1]);
            System.out.println("Relation " + i + " - " + n + " " + m);

        }
    }

}
