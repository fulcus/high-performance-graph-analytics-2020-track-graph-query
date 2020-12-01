package query_engine;

import implementation.HashJoin;
import implementation.HashJoinE;

import java.util.ArrayList;

public class ExecJoin implements IQueryOperation {
    private static HashJoin hash = null;
    private static HashJoinE hashjoine = null;

    public ExecJoin(HashJoin hash) {
        this.hash = hash;
        hashjoine = new HashJoinE();
    }

    @Override
    public ArrayList<Integer[]> execQuery(Integer vertex_id, Integer depth) {
        return hashjoine.join(hash, vertex_id, depth);
    }
}
