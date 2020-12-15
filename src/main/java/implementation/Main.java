package implementation;

import query_engine.QueryEngine;

public class Main {
    private static CSR csr = null;
    private static HashJoin hash = null;
    private static QueryEngine queryengine = null;

    public static void main(String[] args) {
        csr = new CSR();
        hash = new HashJoin();

        if(args.length != 4){
            System.err.println("Wrong number of parameter:");
            System.err.println("graph.jar file_relationships lines_to_read algorithms limit_research");
            System.exit(1);
        }

        String filepath = args[0];
        int linesToRead = 0;
        int limit_research = 0;
        try{
            linesToRead = Integer.parseInt(args[1]);
            limit_research = Integer.parseInt(args[3]);
        }catch (NumberFormatException e){
            System.err.println("Error with the cast in integer of the second argument lines_to_read or the fourth argument limit_research:");
            e.printStackTrace();
            System.exit(2);
        }
        String algorithms = args[2].toLowerCase();
        if (algorithms.length() != 11) {
            System.err.println("Wrong length of algorithm string!");
            System.exit(3);
        }

        if(linesToRead != 0) {
            if(algorithms.contains("b") || algorithms.contains("d"))
                csr.buildFromFile(filepath, linesToRead);

            if(algorithms.contains("j"))
                hash.buildFromFile(filepath, linesToRead);
        } else {
            if(algorithms.contains("b") || algorithms.contains("d"))
                csr.buildFromFile(filepath);

            if(algorithms.contains("j"))
                hash.buildFromFile(filepath);
        }
        System.out.println("Dataset loaded!");

        queryengine = new QueryEngine(algorithms, csr, hash, limit_research);

        while (queryengine.readAndExecQuery()) {
            System.out.println("done");
        }
    }
}
