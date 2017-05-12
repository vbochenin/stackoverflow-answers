package jackson;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Question42971905 {

    String hijackingJson = ")]}'[\n" +
            "  {\n" +
            "    \"address\": {\n" +
            "        \"street\": \"Neuenfelder Str\",\n" +
            "        \"housenumber\": \"13A\",\n" +
            "        \"postalcode\": \"21109\",\n" +
            "        \"city\": \"Hamburg\",\n" +
            "        \"geoLocation\": {\n" +
            "            \"lat\": \"52.092309\",\n" +
            "            \"lng\": \"5.130041\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"distance\": 0\n" +
            "  },\n" +
            "  {\n" +
            "    \"address\": {\n" +
            "        \"street\": \"Umber Str\",\n" +
            "        \"housenumber\": \"2\",\n" +
            "        \"postalcode\": \"22567\",\n" +
            "        \"city\": \"Berlin\",\n" +
            "        \"geoLocation\": {\n" +
            "            \"lat\": \"51.5761166\",\n" +
            "            \"lng\": \"5.0377286\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"distance\": 0\n" +
            "  }]";

    public static class Domain {
        private final Map<String, Object> values = new HashMap<>();

        @JsonAnySetter
        public void addValue(String key, Object value) {
            values.put(key, value);
        }
    }

    @Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getDeserializationConfig().withHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                return super.handleUnknownProperty(ctxt, p, deserializer, beanOrClass, propertyName);
            }

            @Override
            public Object handleUnexpectedToken(DeserializationContext ctxt, Class<?> targetType, JsonToken t, JsonParser p, String failureMsg) throws IOException {
                return super.handleUnexpectedToken(ctxt, targetType, t, p, failureMsg);
            }
        });

        Domain domain = mapper.readValue(hijackingJson, Domain.class);
        Assert.assertFalse(domain.values.isEmpty());
    }

    public static class HijackingDeserializer extends DelegatingDeserializer {
        private final JsonDeserializer<?> deserializer;

        public HijackingDeserializer(JsonDeserializer<?> deserializer) {
            super(deserializer);
            this.deserializer = deserializer;
        }

        @Override
        protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
            return deserializer;
        }

        @Override
        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonParserDelegate jsonParserDelegate = new JsonParserDelegate(jp);
            System.out.println(jsonParserDelegate.getText());
            return _delegatee.deserialize(jp, ctxt);
        }
    }

}
