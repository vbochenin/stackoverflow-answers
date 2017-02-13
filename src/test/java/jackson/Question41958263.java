package jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.reflect.Reflection;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

public class Question41958263 {
    public static class Customers{
        @JsonProperty("values")
        private List<Customer> customers;

    }

    public interface PostConstruct {
        void postConstruct();
    }

    public static class Customer implements PostConstruct {
        @JsonProperty("name")
        private String name;

        @JsonProperty("age")
        private int age;

        @JsonIgnore
        private Map<String, Object> additionalFields = new HashMap<>();

        private Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }


        @JsonAnySetter
        public void setAdditionalValue(String key, Object value) {
            additionalFields.put(key, value);
        }

        @Override
        public void postConstruct() {
            address = new Address();
            address.setStreet(String.valueOf(additionalFields.get("street")));
            address.setPostalcode(String.valueOf(additionalFields.get("postalcode")));
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address{
        private String street;

        private String postalcode;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }
    }

    @Test
    public void testJackson() throws Exception {
        String json = "{\n" +
                " " +
                " \"values\":[{\n" +
                " \"name\":\"Abc\",\n" +
                " \"age\":18,\n" +
                " \"street\":\"test\", \n" +
                " \"postalcode\":\"1231412\"\n" +
                "},\n" +
                " {\n" +
                "  \"name\":\"ccvb\",\n" +
                " \"age\":20,\n" +
                " \"street\":\"test2\", \n" +
                " \"postalcode\":\"123\"\n" +
                "  }\n" +
                "]}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config,
                                                          BeanDescription beanDesc,
                                                          final JsonDeserializer<?> deserializer) {
                return new PostConstructDeserializer(deserializer);
            }
        });
        mapper.registerModule(module);

        Customers c = mapper.readValue(json, Customers.class);

//        Customers c = mapper.readValue(json, Customers.class);
        System.out.println(c);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(
                "{ \"XML\": { \"version\": 1.0, \"encoding\": \"UTF-8\" }, \"Comment\": \"ABC EFG Json\", \"pCt\": { \"pCHead\": \n" +
                        "{ \"Date\": \"9999-12-31\", \"ID \": \"12345 \", \"Type\": \"ABC\", \"prtList\": \n" +
                        "[{ \"cCType\": \"B\", \"cReason\": \"\", \"oInd\": 10 }], \"pNet\": [{ \"seType\": \"3\", \n" +
                        "\"eDate\": \"2016-10-01\", \"exDate\": \"9999-12-31\"\n" +
                        "\n" +
                        "        }]\n" +
                        "    }\n" +
                        "}\n" +
                        "}", new TypeReference<Map<String, Object>>() {
        });

        System.out.println(map);

    }

    @Test
    public float testNumbers() {
        float givennumber = -5;
        float[] array={-10, 1, 2,3, 5, 10, 100};
        float distance = Float.MAX_VALUE;
        int idx = -1;
        for(int c = 0; c < array.length; c++) {
            float cdistance = array[c] - givennumber;
            if (cdistance > 0 && distance > cdistance) {
                distance = cdistance;
                idx = c;
            }
        }
        if (idx < 0) {
            return Float.NaN; //return NaN if array doesn't contains a number (you can use Float.isNaN() to check result of function)
        } else {
            return array[idx]; //return nearest largest;
        }
    }


    public static void main(String... args) {
        try {
            System.out.println("000000");
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("111111");
            System.exit(0);
        } finally {
            System.out.println("2222222");
        }
    }


    @Test
    public void test() {
        Object arr = new Integer[0];
        removeDuplicates((Array) Array.newInstance(Integer.class, 2));
    }

    public static Array removeDuplicates(Array a)
    {
        return (Array) Array.newInstance(Integer.class, 2);
    }

    public static class PostConstructDeserializer extends DelegatingDeserializer {
        private final JsonDeserializer<?> deserializer;

        public PostConstructDeserializer(JsonDeserializer<?> deserializer) {
            super(deserializer);
            this.deserializer = deserializer;
        }

        @Override
        protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
            return deserializer;
        }

        @Override
        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Object result = _delegatee.deserialize(jp, ctxt);
            if (result instanceof PostConstruct) {
                ((PostConstruct) result).postConstruct();
            }
            return result;
        }
    }



    public static class AddressDeserializer extends StdDeserializer<Address> {
        protected AddressDeserializer() {
            super(Address.class);
        }

        @Override
        public Address deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode treeNode = p.readValueAsTree();

            return null;
        }
    }

//    @JsonPOJOBuilder
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class AddressBuilder {
//        private String street;
//        private String postcode;
//
//
//        public AddressBuilder withStreet(String street) {
//            this.street = street;
//            return this;
//        }
//
//        public AddressBuilder withPostalcode(String postcode) {
//            this.postcode = postcode;
//            return this;
//        }
//
//        public Address build() {
//            Address address = new Address();
//            address.setPostalcode(postcode);
//            address.setStreet(street);
//            return address;
//        }
//
//
//    }

}
