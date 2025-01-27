package implementation;

import data.Table;
import engine.HashJoinEngine;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashJoinE implements HashJoinEngine {

    public ArrayList<Integer[]> join(Table tab1, Integer elementId, Integer depth) {
        ArrayList<Integer[]> results = new ArrayList<>();
        Integer[] queryResult = new Integer[depth + 1];
        queryResult[0] = elementId;

        subtraverse(tab1, results, elementId, queryResult, depth);

        return results;
    }

    private void subtraverse(Table tab1, ArrayList<Integer[]> results, Integer elementId, Integer[] queryResult, int depth) {

        if (depth == 0) {
            results.add(queryResult);
            return;
        }

        LinkedList<Integer> sub_neighbors = tab1.getNeighbors(elementId);
        if(sub_neighbors == null)
            return;
        for (Integer node_id : sub_neighbors) {
            Integer[] sub_query_result = queryResult.clone();
            sub_query_result[sub_query_result.length - depth] = node_id;
            subtraverse(tab1, results, node_id, sub_query_result, depth - 1);
        }
    }
}
