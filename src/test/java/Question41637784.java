import one.util.streamex.IntCollector;
import one.util.streamex.IntStreamEx;
import org.junit.Test;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Question41637784 {

    public void test(List<String> initalNodeNames) {
        Stream<Consumer<SomeWriter>> nodeNames = initalNodeNames
                .stream()
                .map((nodeName) -> (Consumer<SomeWriter>) (SomeWriter writer) -> writer.set(nodeName, "pennString"));
    }

    @FunctionalInterface
    private interface SomeWriter {
        void set(String arg0, String arg1);
    }

    @Test
    public void tt() {

        JButton attack1=new JButton("reduce health");

//        attack1.removeActionListener();
//        int elts = 10;
//        Integer[] ints1 = IntStream.range(0, elts)
//                .boxed()
//                .collect(collectingAndThen(toList(),
//                        integers -> {
//                            Collections.shuffle(integers);
//                            return integers.toArray(new Integer[integers.size()]);
//                        }
//                ));
//
//        Random random = new Random();
//        int[] ints2 = IntStreamEx.range(0, elts)
//                .map(IntFun)
//                .sorted((o1, o2) -> random.nextInt())
//                .toArray();
//
//        System.out.print(ints2);

    }
}
