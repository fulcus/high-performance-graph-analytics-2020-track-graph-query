package implementation;

import query_engine.QueryEngine;

public class Main {
    private static CSR csr = null;
    private static HashJoin hash = null;
    private static QueryEngine queryengine = null;

    public static void main(String[] args) {
        csr = new CSR();
        hash = new HashJoin();

        String filepath = args[0];

        csr.buildFromFile(filepath);
        hash.buildFromFile(filepath);

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
