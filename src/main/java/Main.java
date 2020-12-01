import implementation.CSR;
import implementation.HashJoin;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        int depth = readQuery();
        System.out.println(depth);

        CSR csr = new CSR();
        HashJoin hash = new HashJoin();

        csr.buildFromFile("src/main/resources/soc-pokec-relationships.txt");
        hash.buildFromFile("src/main/resources/soc-pokec-relationships.txt");

        /*
        for(int i = 1; i <= 3000; i++) {
            System.out.println("Neighbors of "+i+" node!");
            System.out.println(csr.getNeighbors(i));
            System.out.println(hash.getNeighbors(i));
        }
        */

        /*
        HashJoinE hashJoinE = new HashJoinE();

        for (int i = 1; i <= 1; i++) {
            System.out.println("Traverse of " + i + " node!");
            ArrayList<Integer[]> queryResult = hashJoinE.join(hash, i, 2);

            for (Integer[] integers : queryResult) {
                System.out.println(Arrays.toString(integers));
            }
        }*/

    }


    public static int readQuery() {
        Pattern pattern = Pattern.compile("\\([a-z]\\)(\\-\\>\\([a-z]\\)){0,10}");
        Pattern nodeCount = Pattern.compile("\\([a-z]\\)");

        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        Matcher patternMatcher = pattern.matcher(query);

        while(!patternMatcher.matches()) {
            System.out.println("Wrong input, try again");
            query = scanner.nextLine();
            patternMatcher = pattern.matcher(query);
        }

        Matcher nodeMatcher = nodeCount.matcher(query);

        System.out.println();

        int count = 0;
        while (nodeMatcher.find()) {
            count++;
        }
        return count;
    }
}
