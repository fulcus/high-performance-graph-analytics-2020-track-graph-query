package query_engine;

import implementation.CSR;
import implementation.CSRE;

import java.util.ArrayList;

public class ExecTraverseDFS implements IQueryOperation {
    private static CSR csr = null;
    private static CSRE csre = null;

    public ExecTraverseDFS(CSR csr) {
        this.csr = csr;
        csre = new CSRE();
    }

    @Override
    public ArrayList<Integer[]> execQuery(Integer vertex_id, Integer depth) {
        return csre.traverse(csr, vertex_id, depth, "dfs");
    }
}
