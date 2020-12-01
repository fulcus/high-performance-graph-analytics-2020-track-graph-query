package implementation;

import query_engine.QueryEngine;

public class Main {
    private static CSR csr = null;
    private static HashJoin hash = null;
    private static QueryEngine queryengine = null;

    public static void main(String[] args) {
        csr = new CSR();
        hash = new HashJoin();

        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");
        hash.buildFromFile("src/main/resources/soc-pokec-relationships.txt");

        queryengine = new QueryEngine(csr, hash);

        /* for(int i = 1; i <= 3000; i++) {
            System.out.println("Neighbors of "+i+" node!");
            System.out.println(csr.getNeighbors(i));
        } */

        do {
            System.out.println("Write a query:");
        } while (queryengine.readAndExecQuery());
    }
}
