package implementation;

import data.CompressedSparseRow;
import engine.CSREngine;

import java.util.ArrayList;

public class CSRE implements CSREngine {
    @Override
    public ArrayList<Integer[]> traverse(CompressedSparseRow csr, Integer vertex_id, Integer depth, String type_search) {
        ArrayList<Integer[]> results = new ArrayList<>();

        switch (type_search) {
            case "dfs":
                Integer[] query_result = new Integer[depth+1];
                query_result[0] = vertex_id;

                sub_traverse_dfs(csr, results, vertex_id, query_result, depth);
                break;
            case "bfs":
                Integer[] array_vertex = new Integer[depth+1];
                array_vertex[0] = vertex_id;
                ArrayList<Integer[]> base_vertex_query_results = new ArrayList<>();
                base_vertex_query_results.add(array_vertex);

                results = sub_traverse_bfs(csr, base_vertex_query_results, depth);
                break;
            default:
                return null;
        }

        return results;
    }

    private void sub_traverse_dfs(CompressedSparseRow csr, ArrayList<Integer[]> results, Integer vertex_id, Integer[] query_result, int depth){
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
            sub_traverse_dfs(csr, results, node_id, sub_query_result, depth - 1);
        }
    }

    private ArrayList<Integer[]> sub_traverse_bfs(CompressedSparseRow csr, ArrayList<Integer[]> vertex_query_results, int depth) {
        if(depth == 0){
            ArrayList<Integer[]> results = new ArrayList<>();
            results:
            for (Integer[] query_result: vertex_query_results) {
                for (int i = 0; i < query_result.length; i++) {
                    for (int j = i + 1; j < query_result.length; j++) {
                        //TODO: Test with int data type instead of Integer
                        if (query_result[i] == query_result[j])
                            continue results;
                    }
                }
                results.add(query_result);
            }
            return results;
        }


        ArrayList<Integer[]> sub_vertex_query_result = new ArrayList<>();
        for (Integer[] vertex_query_result : vertex_query_results) {
            ArrayList<Integer> sub_neighbors = csr.getNeighbors(vertex_query_result[vertex_query_result.length - depth - 1]);
            for (Integer node_id : sub_neighbors) {
                Integer[] sub_query_result = vertex_query_result.clone();
                sub_query_result[sub_query_result.length - depth] = node_id;
                sub_vertex_query_result.add(sub_query_result);
            }
        }

        return sub_traverse_bfs(csr, sub_vertex_query_result, depth - 1);
    }
}
