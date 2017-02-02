package dynamic.programming;

import com.google.common.collect.ImmutableMap;
import one.util.streamex.StreamEx;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(value = SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = {Question41895575.CompressorConfig.class, Question41895575.TaskConfig.class})
@TestPropertySource(properties = {"file.rollover.sink.compress=nope"})
public class Question41895575 {

    @Autowired
    private  RolloverTask rolloverTask;

    @Test
    public void testDeltas() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(ImmutableMap.of("key1", new Object(), "key2", new Object()));

        addDeltas(map);
    }

    public Map<String, Object> addDeltas(Map<String, Object> deltas) {
        Map<String, Object> result = new HashMap<>();
        deltas.replaceAll((key, value) -> processObject(value));
        return result;
    }

    public Map<String, Object> addDeltasStreamEx(Map<String, Object> deltas) {
        deltas.forEach((key, value) -> deltas.put(key, processObject(value)));
        return deltas;
    }

    private Object processObject(Object value) {
        return new Object();
    }

    @Test
    public void testRollover() {
        Assert.assertThat(rolloverTask.fileCompressor, Matchers.is(Matchers.nullValue()));
    }

    public static class CompressorConfig {
        @Bean
        @ConditionalOnProperty(prefix = "file.rollover.sink", name = "compress", havingValue = "yep", matchIfMissing = true)
        public FileCompressor fileCompressor() {
            return new DefaultFileCompressor();
        }

        private class DefaultFileCompressor extends FileCompressor {
        }
    }

    private static class FileCompressor {
    }

    public static class TaskConfig {
        @Bean
        public IntervalCalculator intervalCalculator() {
            return new IntervalCalculator();
        }

        @Autowired(required = false)
        private FileCompressor fileCompressor;

        @Bean
        @ConditionalOnProperty(prefix = "file.rollover.sink", name = "compress", havingValue = "nope", matchIfMissing = false)
        public RolloverTask rolloverTask(IntervalCalculator intervalCalculator) {
            return new RolloverTask(intervalCalculator, null);
        }
        @Bean
        @ConditionalOnProperty(prefix = "file.rollover.sink", name = "compress", havingValue = "yep", matchIfMissing = true)
        public RolloverTask rolloverTaskWithCompressor(final IntervalCalculator intervalCalculator, final FileCompressor fileCompressor) {
            return new RolloverTask(intervalCalculator, fileCompressor);
       }
    }

    private static class RolloverTask {
        private final IntervalCalculator intervalCalculator;
        private final FileCompressor fileCompressor;

        public RolloverTask(IntervalCalculator intervalCalculator, FileCompressor fileCompressor) {

            this.intervalCalculator = intervalCalculator;
            this.fileCompressor = fileCompressor;
        }
    }

    private static class IntervalCalculator {
    }
}
