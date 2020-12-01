package data;

import java.util.ArrayList;

public interface Table {

  /**
   * Builds the HashJoin by reading information from soc-pokec-relationships.txt
   *
   * @param filepath path of input file
   */
  void buildFromFile(String filepath);

  ArrayList<Integer> getNeighbors(Integer vertex_id);

}
