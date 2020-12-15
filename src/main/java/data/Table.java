package data;

import java.util.LinkedList;

public interface Table {

  /**
   * Builds the HashJoin by reading information from soc-pokec-relationships.txt
   *
   * @param filepath path of input file
   */
  void buildFromFile(String filepath);

  LinkedList<Integer> getNeighbors(Integer vertex_id);

}
