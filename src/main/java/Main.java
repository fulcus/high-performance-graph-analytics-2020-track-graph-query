import data.CSR;

public class Main {
    public static void main(String[] args) {
        CSR csr = new CSR();
        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");

        for(int i = 1; i <= 3000; i++) {
            System.out.println("Neighbors of "+i+" node!");
            System.out.println(csr.getNeighbors(i));
        }
    }
}
