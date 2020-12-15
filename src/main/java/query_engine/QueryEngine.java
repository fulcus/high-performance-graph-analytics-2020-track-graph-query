package query_engine;

import implementation.CSR;
import implementation.HashJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class QueryEngine {
    private CSR csr = null;
    private HashJoin hash = null;

    private int limit_research = 3000; // limit of max number of nodes for the research

    private ExecJoin execjoin = null;
    private ExecTraverseBFS exectraversebfs = null;
    private ExecTraverseDFS exectraversedfs = null;

    private String algorithms = null;

    // Basic constructor
    public QueryEngine(String algorithms, CSR csr, HashJoin hash) {
        this.algorithms = algorithms;
        this.csr = csr;
        this.hash = hash;

        execjoin = new ExecJoin(hash);
        exectraversebfs = new ExecTraverseBFS(csr);
        exectraversedfs = new ExecTraverseDFS(csr);
    }

    // Useful to set the max number of nodes for the research
    public QueryEngine(String algorithms, CSR csr, HashJoin hash, int limit_research) {
        this(algorithms, csr, hash);
        this.limit_research = limit_research;
    }

    // Read and execute one query from stdin
    public boolean readAndExecQuery() {
        int depth = readQueryDeep();

        // If 0 the query was written wrong, if -1 the user has written the exit command
        if(depth == 0) {
            return true;
        } else if (depth == -1) {
            return false;
        }

        // Select the best operation to exec the query in base of the depth
        IQueryOperation operation = null;
        switch (algorithms.charAt(depth - 1)) {
            case 'b':
                operation = exectraversebfs;
                break;
            case 'j':
                operation = execjoin;
                break;
            case 'd':
                operation = exectraversedfs;
                break;
            default:
                System.err.println("Format error of algorithm string!");
                System.exit(3);
        }

        // Exec the query
        for(int i = 1; i <= limit_research; i++) {
            // System.out.println("Traverse of " + i + " node!");
            ArrayList<Integer[]> query_result = operation.execQuery(i, depth);
            /* for (Integer[] integers : query_result) {
                System.out.println(Arrays.toString(integers));
            } */
        }

        return true;
    }

    // Read one query from stdin and calculate the deep of it
    private int readQueryDeep() {
        Pattern regex_query_input = Pattern.compile("\\([a-z]\\)(\\-\\>\\([a-z]\\)){1,10}");

        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        if(query.equals("exit"))
            return -1;

        if (!regex_query_input.matcher(query).matches()) {
            System.out.println("Wrong input, try again");
            return 0;
        }

        return query.split("\\)").length-2;
    }
}
