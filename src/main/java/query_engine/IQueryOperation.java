package query_engine;

import java.util.ArrayList;

public interface IQueryOperation {
    public ArrayList<Integer[]> execQuery(Integer vertex_id, Integer depth);
}
