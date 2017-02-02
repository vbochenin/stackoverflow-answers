package dynamic.programming;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The function main accepts an array of strings as an arguement, and will print a single line of output. the output will be the input in an order such that the first letter of each string is the same as the last letter of the previous string but also the last letter of last string is same as first letter of first string. If no order exists that include all strings, then the code will print no solution
 * <p>
 * Example input and outputs:
 * <p>
 * input: squirrel eyes klondlike lasik
 * <p>
 * output: squirrel lasik klondlike eyes
 * <p>
 * input: why cant we dance
 * <p>
 * output: NO SOLUTION
 * <p>
 * input: apple tampa elephant
 * <p>
 * output:apple elephant tampa
 *
 * @author Vlad Bochenin
 * @since 24/01/2017.
 */
public class Ring {

    private String[] findRing(String... words) {
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String[] result = findRingForHeadAndTail(word, dropElementWithIndex(i, words));
            if ((result.length == words.length - 1) &&
                    (word.charAt(0) == result[result.length - 1].charAt(result[result.length - 1].length() - 1))) { //word started from last letter in tail
                return concatHeadAndTail(word, result);
            }
        }
        return new String[]{"NO SOLUTION"};
    }

    private String[] findRingForHeadAndTail(
            String head, //first word
            String... tail //other words
    ) {
        if (tail.length == 0) { // if we don't have other words then just return empty array
            return new String[0];
        }

        if (tail.length == 1) { // if we have just one word in tail
            if (tail[0].charAt(0) == head.charAt(head.length() - 1)) { //and this word begins with last letter in head
                return tail; //return this tail
            } else {
                return new String[0]; //otherwise return empty array
            }
        }

        for (int i = 0; i < tail.length; i++) { // for every word
            String word = tail[i];
            if (word.charAt(0) == head.charAt(head.length() - 1)) { // if this word begins with last letter in head
                String[] result = findRingForHeadAndTail( //recursive call for
                        word, //picked word
                        dropElementWithIndex(i, tail) // all other words in tail
                );
                if (result.length == tail.length - 1) { // if recursive call returns the same amount of words as we passed there as tail
                    return concatHeadAndTail(word, result); //return array {head, tail}
                }
            }
        }
        return new String[0];
    }

    //returns array as {head, tail}
    private String[] concatHeadAndTail(String head, String... tail) {
        return Stream.concat(Stream.of(head), Stream.of(tail)).collect(Collectors.toList()).toArray(new String[tail.length + 1]);
    }

    //removes element with index i from words
    private String[] dropElementWithIndex(int i, String... words) {
        List<String> result = new ArrayList<>();
        for (int j = 0; j < words.length; j++) {
            if (j != i) {
                result.add(words[j]);
            }
        }
        return result.toArray(new String[words.length - 1]);
    }

    @Test
    public void findRing() {
        System.out.println(Arrays.toString(findRing("squirrel", "eyes", "klondlike", "lasik")));
        System.out.println(Arrays.toString(findRing("why", "cant", "we", "dance")));
        System.out.println(Arrays.toString(findRing("apple", "tampa", "elephant")));
        System.out.println(Arrays.toString(findRing("apple", "elephant", "tampa")));
        System.out.println(Arrays.toString(findRing("elephant", "apple", "tampa")));
        System.out.println(Arrays.toString(findRing("alfa", "alfa", "beta")));
        System.out.println(Arrays.toString(findRing("alfa", "alfa")));
        System.out.println(Arrays.toString(findRing("alfa", "alfa", "bravo")));
        System.out.println(Arrays.toString(findRing("apple", "tampa", "tad", "dot", "elephant")));
    }


    @Test
    public void findRingWithMatrix() {
        findRingWithMatrix("apple", "tampa", "tad", "dot", "elephant");
        findRingWithMatrix("squirrel", "eyes", "klondlike", "lasik");
        findRingWithMatrix("why", "cant", "we", "dance");
        findRingWithMatrix("apple", "tampa", "elephant");
        findRingWithMatrix("apple", "elephant", "tampa");
        findRingWithMatrix("elephant", "apple", "tampa");
        findRingWithMatrix("alfa", "alfa", "beta");
        findRingWithMatrix("alfa", "alfa");
        findRingWithMatrix("alfa");
        findRingWithMatrix("alfa", "alfa", "bravo");
    }

    private void findRingWithMatrix(String... words) {
        System.out.println("For example: " + Arrays.toString(words));
        boolean[][] initialAdjacencyMatrix = new boolean[words.length][words.length];
        List<String[]> paths = new ArrayList<>();
        //Build initial adjacency matrix
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                initialAdjacencyMatrix[i][j] = (i != j) && (couldBeInChain(words[i], words[j]));
                //if node is reachable
                if (initialAdjacencyMatrix[i][j]) {
                    //put this path to possible paths
                    paths.add(new String[]{words[i], words[j]});
                }
            }
        }

        //create temporary copy of matrix to multiply
        boolean[][] tempAdjacencyMatrix = initialAdjacencyMatrix.clone();

        //We should multiply matrix N-1 times, because first step in graph we've already done on previous step
        for (int n = 1; n < words.length; n++) {
            boolean[][] bufferAdjacencyMatrix = new boolean[words.length][words.length];

            List<String[]> newPathes = new ArrayList<>();

            //multiply matrices (next step and initial). In result we get true in node which is reachable from first one in N steps
            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j < words.length; j++) {
                    for (int k = 0; k < words.length; k++) {
                        bufferAdjacencyMatrix[i][j] |= (tempAdjacencyMatrix[i][k] && initialAdjacencyMatrix[k][j]);
                    }
                    //if node reachable
                    if (bufferAdjacencyMatrix[i][j]) {
                        //create new path and put it list of possible paths
                        for (String[] path : paths) {
                            if (couldBeInChain(path[path.length - 1], words[j])) {
                                String[] newPath = new String[path.length + 1];
                                System.arraycopy(path, 0, newPath, 0, path.length);
                                newPath[newPath.length - 1] = words[j];
                                newPathes.add(newPath);
                            }
                        }
                    }
                }
            }
            paths = removeDuplicates(newPathes);
            System.out.println("Step #" + n);
            printMatrix(bufferAdjacencyMatrix);
            tempAdjacencyMatrix = bufferAdjacencyMatrix;
        }

        boolean isRing = true;

        //Ring could by just if after (N-1) multiplications we have [true] in main diagonal.
        //In other words, can reach node from which we started in N-1 steps.
        for (int i = 0; i < words.length; i++) {
            isRing = tempAdjacencyMatrix[i][i];
            if (!isRing) {
                break;
            }
        }

        if (!isRing) {
            System.out.println("NO SOLUTION");
            return;
        } else {

            System.out.println("Found solutions:");
            for (String[] path : paths) {
                //we are interested just in solutions when first node is equals to last one
                if (path[0].equals(path[path.length - 1])) {
                    String[] toPrint = new String[path.length - 1];
                    System.arraycopy(path, 0, toPrint, 0, toPrint.length);
                    System.out.println(Arrays.deepToString(toPrint));
                }
            }
        }

        System.out.println("==============================");
    }

    private List<String[]> removeDuplicates(List<String[]> newPathes) {
        return newPathes
                .stream()
                .map(Arrays::asList)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), new Function<Set<List<String>>, List<String[]>>() {
                    @Override
                    public List<String[]> apply(Set<List<String>> lists) {
                        List<String[]> result = new ArrayList<>();
                        for (List<String> list : lists) {
                            result.add(list.toArray(new String[list.size()]));
                        }
                        return result;
                    }
                }));
    }

    private boolean couldBeInChain(String first, String second) {
        return first.charAt(first.length() - 1) == second.charAt(0);
    }

    private void printMatrix(boolean[][] matrix) {
        System.out.println("-------------------------------");

        for (boolean[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("-------------------------------");
    }


}
