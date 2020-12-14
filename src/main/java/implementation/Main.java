package implementation;

import query_engine.QueryEngine;

public class Main {
    private static CSR csr = null;
    private static HashJoin hash = null;
    private static QueryEngine queryengine = null;

    public static void main(String[] args) {
        csr = new CSR();
        hash = new HashJoin();

        if(args.length != 2){
            System.err.println("Wrong number of parameter:");
            System.err.println("graph.jar file_relationships lines_to_read");
            System.exit(1);
        }

        String filepath = args[0];
        int linesToRead = 0;
        try{
            linesToRead = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.err.println("Error with the cast in integer of the second argument lines_to_read:");
            e.printStackTrace();
            System.exit(2);
        }

        if(linesToRead != 0) {
            csr.buildFromFile(filepath, linesToRead);
            hash.buildFromFile(filepath, linesToRead);
        } else {
            csr.buildFromFile(filepath);
            hash.buildFromFile(filepath);
        }

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
