package jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Question42022981 {
    @JsonPropertyOrder({"type", "someInfo"})
    public static class InsuredAmount implements Productable, Serializable {

        private static final long serialVersionUID = 1L;

        private IAType type;

        private String someInfo;

        public InsuredAmount() {
        }

        public InsuredAmount(IAType typeA, String someInfo) {
            this.type = typeA;
            this.someInfo = someInfo;

        }

//        public void insertData(String className, String fileName) {
//            Class clazz = Class.forName(className);
//
//            List newList = new ArrayList();
//            if (clazz.isInstance(obj)) {
//                newList.add(obj);
//            }
//        }
//
//        public void insertData(String className, String fileName) {
//            Class clazz = Class.forName(className);
//            ArrayList<clazz> newList = new ArrayList<class>();
//        }

        /* This should be on the product level, but if I can solve this problem,
        the next level will just be more of the same.
        */
        @JsonIgnore
        @Override
        public Product getProduct() {
            return Product.PROD_A;
        }

        public IAType getType() {
            return type;
        }

        public String getSomeInfo() {
            return someInfo;
        }

        public void setType(IAType type) {
            this.type = type;
        }

        public void setSomeInfo(String someInfo) {
            this.someInfo = someInfo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InsuredAmount that = (InsuredAmount) o;

            if (type != null ? !type.equals(that.type) : that.type != null) return false;
            return someInfo != null ? someInfo.equals(that.someInfo) : that.someInfo == null;

        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (someInfo != null ? someInfo.hashCode() : 0);
            return result;
        }
    }

    public interface Productable {

        public Product getProduct();

    }

    public enum Product {

        PROD_A, PROD_B;

    }

    public interface IAType extends Productable {

    }

    public enum IATypeA implements IAType {

        FOO, BAR;

        @Override
        public Product getProduct() {
            return Product.PROD_A;
        }
    }

    public enum IATypeB implements IAType {

        BAR, BOB;

        @Override
        public Product getProduct() {
            return Product.PROD_B;
        }
    }


    public static class IATypeDeserializer extends StdDeserializer<IAType> {

        private static final long serialVersionUID = 1L;
        private List<Class<? extends IAType>> enums;

        public IATypeDeserializer(List<Class<? extends IAType>> enums) {
            super(IAType.class);
            this.enums = enums;
        }

        @Override
        public IAType deserialize(JsonParser parser, DeserializationContext context)
                throws IOException, JsonProcessingException {
            JsonNode node = parser.getCodec().readTree(parser);

            for (Class<? extends IAType> anEnum : enums) {
//                if

            }


        /* How to find out that the class calling the deserialization is InsuredAmountA, which
        has getProduct() method that returns PROD_A, and matches the IATypeA that also returns
        PROD_A, so I know to deserialize IATypeA, instead of other implementations of the IAType
        interface?
        */
            return IATypeA.valueOf(node.asText());
        }
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        List<Class<? extends IAType>> enums = new ArrayList<>();
        enums.add(IATypeA.class);
        enums.add(IATypeB.class);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(IAType.class, new IATypeDeserializer(enums));
//        mapper.registerModule(simpleModule)

        InsuredAmount iaA = new InsuredAmount(IATypeA.FOO, "test it");
        String json = mapper.writeValueAsString(iaA);
        assertThat(json, is("{\"type\":\"FOO\",\"someInfo\":\"test it\"}"));

        InsuredAmount iaA2 = mapper.readValue(json, InsuredAmount.class);
        IAType type = iaA2.getType();
        assertThat(type, is(IATypeA.FOO));
        assertThat(type.getProduct(), is(Product.PROD_A));
        assertThat(iaA, is(iaA2));
    }

    @Test
    public void test02() throws IOException {
        InsuredAmount iaA = new InsuredAmount(IATypeA.BAR, "test it");
        String json = mapper.writeValueAsString(iaA);
        assertThat(json, is("{\"type\":\"BAR\",\"someInfo\":\"test it\"}"));
        InsuredAmount iaA2 = mapper.readValue(json, InsuredAmount.class);
        assertThat(iaA, is(iaA2));
    }
}
