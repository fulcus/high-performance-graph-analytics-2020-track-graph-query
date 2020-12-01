package implementation;

import data.CompressedSparseRow;
import engine.CSREngine;

import java.util.ArrayList;

public class CSRE implements CSREngine {
    @Override
    public ArrayList<Integer[]> traverse(CompressedSparseRow csr, Integer vertex_id, Integer depth) {
        ArrayList<Integer[]> results = new ArrayList<>();
        Integer[] query_result = new Integer[depth+1];
        query_result[0] = vertex_id;

        sub_traverse(csr, results, vertex_id, query_result, depth);

        return results;
    }

    private void sub_traverse(CompressedSparseRow csr, ArrayList<Integer[]> results, Integer vertex_id, Integer[] query_result, int depth){
        if(depth == 0){
            for (int i = 0; i < query_result.length; i++) {
                for (int j = i + 1; j < query_result.length; j++) {
                    //TODO: Test with int data type instead of Integer
                    if (query_result[i] == query_result[j])
                        return;
                }
            }
            results.add(query_result);
            return;
        }

        ArrayList<Integer> sub_neighbors = csr.getNeighbors(vertex_id);
        for(Integer node_id : sub_neighbors){
            Integer[] sub_query_result = query_result.clone();
            sub_query_result[sub_query_result.length - depth] = node_id;
            sub_traverse(csr, results, node_id, sub_query_result, depth - 1);
        }
    }
}
