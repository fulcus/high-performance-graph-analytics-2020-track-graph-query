package data;

import java.util.ArrayList;

public interface CompressedSparseRow {

  /**
   * Builds the CSR by reading information from soc-pokec-relationships.txt
   * @param filepath path of input file
   */
  public void buildFromFile(String filepath);

  public ArrayList<Integer> getNeighbors(Integer vertex_id);

  /* Feel free to add any other useful method you can think of */
}
