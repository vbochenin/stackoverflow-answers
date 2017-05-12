package jackson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Question42347970 {

    public static class A {

        private final Map<String, Object> values = new HashMap<>();

        @JsonAnySetter
        public void addField(String key, Object value) {
            values.put(key, value);
        }

        @JsonAnyGetter
        public Object getField(String key) {
            return values.get(key);
        }
    }


    @Test
    public void test() throws Exception {
        String json = "{\"commonField\" : \"value\", \"customField\" : \"anotherValue\"}";

        ObjectMapper mapper = new ObjectMapper();
        A a = mapper.readValue(json, A.class);

        Assert.assertEquals("value", a.getField("commonField"));
        Assert.assertEquals("anotherValue", a.getField("customField"));

        System.out.println(mapper.writeValueAsString(a));
    }
}
