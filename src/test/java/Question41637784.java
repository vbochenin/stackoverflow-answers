import java.io.Writer;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Question41637784 {

    public void test(List<String> initalNodeNames) {
        Stream<Consumer<SomeWriter>> nodeNames = initalNodeNames
                .stream()
                .map((nodeName) -> (Consumer<SomeWriter>) someWriter -> someWriter.set(nodeName, "pennString"));
    }

    @FunctionalInterface
    private interface SomeWriter {
        void set(String arg0, String arg1);
    }
}
