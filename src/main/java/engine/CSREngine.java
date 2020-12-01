package engine;

import java.util.ArrayList;

import data.CompressedSparseRow;

public interface CSREngine {

  public ArrayList<Integer[]> traverse(CompressedSparseRow csr, Integer vertex_id, Integer depth, String type_search);

  /* Feel free to add any other useful method you can think of */
}
