package implementation;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        CSR csr = new CSR();
        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");

        /* for(int i = 1; i <= 3000; i++) {
            System.out.println("Neighbors of "+i+" node!");
            System.out.println(csr.getNeighbors(i));
        } */

        CSRE csre = new CSRE();
        for(int i = 1; i <= 3000; i++) {
            System.out.println("Traverse of " + i + " node!");
            ArrayList<Integer[]> query_result = csre.traverse(csr, i, 2);
            for (Integer[] integers : query_result) {
                System.out.println(Arrays.toString(integers));
            }
        }
    }
}
