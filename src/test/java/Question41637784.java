import java.io.Writer;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Question41637784 {

    public void test(List<String> initalNodeNames) {
        Stream<Consumer<SomeWriter>> nodeNames = initalNodeNames
                .stream()
                .map((nodeName) -> (Consumer<SomeWriter>) (SomeWriter writer)  -> writer.set(nodeName, "pennString"));
    }

    @FunctionalInterface
    private interface SomeWriter {
        void set(String arg0, String arg1);
    }
}
