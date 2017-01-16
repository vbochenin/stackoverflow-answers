import ma.glasnost.orika.CustomConverter;
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

public class Question41674023 {
    public static class A {
        private String stringA;

        public String getStringA() {
            return stringA;
        }

        public void setStringA(String stringA) {
            this.stringA = stringA;
        }

    }

    public static class B {

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
            ConverterFactory converterFactory = factory.getConverterFactory();
            converterFactory.registerConverter("stringToListConverter", new StringToSomeObjectConverter());

            factory.classMap(A.class, B.class) //
                    .fieldMap("stringA", "someObjects").converter("stringToListConverter").add()
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

        B outcome = new MyMapper().map(a, B.class);

        Assert.assertThat(outcome.getSomeObjects().size(), CoreMatchers.is(1));
    }
}
