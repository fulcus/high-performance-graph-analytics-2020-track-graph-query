package engine;

import data.Table;

import java.util.ArrayList;

public interface HashJoinEngine {
    ArrayList<Integer[]> join(Table tab1, Integer element_id, Integer depth);
}
