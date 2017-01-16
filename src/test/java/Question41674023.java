import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.Type;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class Question41674023 {
    public static class A {
        private String stringA;
        private Integer numA;

        public String getStringA() {
            return stringA;
        }

        public void setStringA(String stringA) {
            this.stringA = stringA;
        }

        public Integer getNumA() {
            return numA;
        }

        public void setNumA(Integer numA) {
            this.numA = numA;
        }
    }

    public static class B {
        private Integer numB;

        List<SomeObject> someObjects;

        public List<SomeObject> getSomeObjects() {
            if (someObjects == null) {
                someObjects = new ArrayList<SomeObject>();
            }
            return someObjects;
        }

        public void setSomeObjects(List<SomeObject> someObjects) {
            this.someObjects = someObjects;
        }

        public Integer getNumB() {
            return numB;
        }

        public void setNumB(Integer numB) {
            this.numB = numB;
        }
    }

    public static class SomeObject {
        private String stringSomeObject;

        public String getStringSomeObject() {
            return stringSomeObject;
        }

        public void setStringSomeObject(String stringSomeObject) {
            this.stringSomeObject = stringSomeObject;
        }
    }

    public static class MyMapper extends ConfigurableMapper {

        @Override
        protected void configure(MapperFactory factory) {
            factory.classMap(A.class, B.class)
                    .field("stringA", "someObjects[0].stringSomeObject")
                    .field("numA", "numB")
                    .byDefault()
                    .register();
        }

    }

    public static class StringToSomeObjectConverter extends CustomConverter<String, List<SomeObject>> {

        private static final String BORROWER_PARTY_TYP_CODE = "147";

        public List<SomeObject> convert(String source, Type<? extends List<SomeObject>> type, MappingContext mappingContext) {
            SomeObject someObject = new SomeObject();
            someObject.setStringSomeObject(source);
            return Arrays.asList(someObject);
        }

        public List<SomeObject> convert(String source, Type<? extends List<SomeObject>> destinationType) {
            return this.convert(source, destinationType, null);
        }
    }

    @Test
    public void testMap() throws Exception {
        A a = new A();
        a.setStringA("a");
        a.setNumA(42);

        B outcome = new MyMapper().map(a, B.class);

        Assert.assertThat(outcome.getSomeObjects().size(), is(1));
        Assert.assertThat(outcome.numB, is(a.getNumA()));

    }
}
