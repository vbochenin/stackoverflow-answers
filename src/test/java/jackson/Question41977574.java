package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static javafx.scene.input.KeyCode.F;

public class Question41977574 {

    @Test
    public void test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        SimpleDeserializers deserializers = new SimpleDeserializers();
        Map<Class<?>, JsonDeserializer<?>> deserializerMap = new HashMap<>();
        deserializerMap.put(new Foo().getClass(), new MyDeserializer());
        deserializers.addDeserializers(deserializerMap);
        objectMapper.registerModule(module);

        Json json = objectMapper.readValue("{\"value\" : \"FOO\", \"value2\" : \"value2\"}", Json.class);
        System.out.println(json);


        List<EdgeTuple> tuple = new ArrayList<>();
        SortedSet<EdgeTuple> sortedSet = new TreeSet<>(tuple);

    }

    public static class Tuple  {

    }
    public static class EdgeTuple extends Tuple implements Comparable<EdgeTuple> {
        int label1;
        int label2;
        public EdgeTuple(int label1, int label2){
            int min = Math.min(label1, label2);
            int max = Math.max(label1, label2);
            this.label1 = min;
            this.label2 = max;
        }


        @Override
        public int compareTo(EdgeTuple o) {
            return this.label1 - o.label1;
        }
    }

    public static class Json {
        @JsonProperty("value")
        private Class<? extends Foo> value;

        @JsonProperty("value2")
        private String value2;
    }

    public static class Foo {

    }

    public static class Bar extends Foo {

    }

    public static class MyDeserializer extends StdDeserializer<Class<? extends Foo>> {
        public MyDeserializer() {
            super(new Foo().getClass());
        }

        public Class<? extends Foo> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String token = jp.getText();
            System.out.println(token);
            switch (token) {
                case "FOO":
                    return Foo.class;
                case "BAR":
                    return Bar.class;
            }
            return null;
        }
    }
}
