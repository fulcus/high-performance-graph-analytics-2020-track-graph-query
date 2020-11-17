package data;

public interface Table {

  /**
   * Builds the HashJoin by reading information from soc-pokec-relationships.txt
   *
   * @param filepath path of input file
   */
  public void buildFromFile(String filepath);

  /* Feel free to add any other useful method you can think of */
}
