package jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static javafx.scene.input.KeyCode.T;

public class Question42348140 {

    @JsonDeserialize(using = ADeserializer.class)
    public static abstract class A {
        String commonField;
    }

    public static class B extends A {
    }

    public static class C extends A {
        String customField;
    }

    @Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(A.class, new ADeserializer());
        mapper.registerModule(module);

        String jsonB = "{\"commonField\" : \"value\"}";
        Assert.assertTrue(mapper.readValue(jsonB, A.class) instanceof B);
        String jsonBNull = "{\"commonField\" : \"value\", \"customField\" : null}";
        Assert.assertTrue(mapper.readValue(jsonBNull, A.class) instanceof B);
        String jsonC = "{\"commonField\" : \"value\", \"customField\" : \"anotherValue\"}";
        Assert.assertTrue(mapper.readValue(jsonC, A.class) instanceof C);
    }

    public static class ADeserializer extends StdDeserializer<A> {
        public ADeserializer() {
            super(A.class);
        }

        @Override
        public A deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.readValueAsTree();
            JsonNode customField = node.findValue("customField");
            A result;
            if (customField != null && !customField.isNull()) {
                result = new C();
                ((C)result).customField = customField.asText();
            } else {
                result = new B();
            }
            result.commonField = node.findValue("commonField").asText();
            return result;
        }
    }
}
