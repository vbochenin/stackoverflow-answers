import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.Type;
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
            ConverterFactory converterFactory = factory.getConverterFactory();
            converterFactory.registerConverter("stringToSomeObjectConverter", new StringToSomeObjectConverter());
            factory.classMap(A.class, B.class)
                    .fieldMap("stringA", "someObjects").converter("stringToSomeObjectConverter").add()
                    .field("numA", "numB")
                    .register();
        }

    }

    public static class MyCustomizedMapper extends ConfigurableMapper {

        @Override
        protected void configure(MapperFactory factory) {
            factory.classMap(A.class, B.class)
                    .customize(
                            new CustomMapper<A, B>() {
                                @Override
                                public void mapAtoB(A a, B b, MappingContext context) {
                                    SomeObject someObject = new SomeObject();
                                    someObject.setStringSomeObject(a.getStringA());
                                    b.getSomeObjects().add(someObject);
                                }
                            }
                    )
                    .field("numA", "numB")
                    .byDefault()
                    .register();
        }

    }


    public static class MyArraySyntaxMapper extends ConfigurableMapper {

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
        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
        A a = new A();
        a.setStringA("a");
        a.setNumA(42);

        B outcome = new MyMapper().map(a, B.class);

        Assert.assertThat(outcome.getSomeObjects().size(), is(1));
        Assert.assertThat(outcome.numB, is(a.getNumA()));
    }

    @Test
    public void testMapArraySytax() throws Exception {
        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
        A a = new A();
        a.setStringA("a");
        a.setNumA(42);

        B outcome = new MyArraySyntaxMapper().map(a, B.class);

        Assert.assertThat(outcome.getSomeObjects().size(), is(1));
        Assert.assertThat(outcome.numB, is(a.getNumA()));
    }

    @Test
    public void testCustomizedMap() throws Exception {
        // Write out source files to (classpath:)/ma/glasnost/orika/generated/
        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");

// Write out class files to (classpath:)/ma/glasnost/orika/generated/
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
        A a = new A();
        a.setStringA("a");
        a.setNumA(42);

        B outcome = new MyCustomizedMapper().map(a, B.class);

        Assert.assertThat(outcome.getSomeObjects().size(), is(1));
        Assert.assertThat(outcome.numB, is(a.getNumA()));
    }

//
//    public void tt() {
//        if (!(((java.lang.String) source.getStringA()) == null)) {
//            if (((((java.util.List) destination.getSomeObjects()).size() <= 0 || ((Question41674023.SomeObject) ((java.util.List) destination.getSomeObjects()).get(0)) == null))) {
//                ((java.util.List) destination.getSomeObjects()).add(0, ((Question41674023.SomeObject) ((ma.glasnost.orika.BoundMapperFacade) usedMapperFacades[0]).newObject(((java.lang.String) source.getStringA()), mappingContext)));
//            }
//        }
//
//        if (!(((java.lang.String) source.getStringA()) == null)) {
//            ((Question41674023.SomeObject) ((java.util.List) destination.getSomeObjects()).get(0)).setStringSomeObject(((java.lang.String) source.getStringA()));
//        } else if (!(((java.util.List) destination.getSomeObjects()) == null) && !((((java.util.List) destination.getSomeObjects()).size() <= 0 || ((Question41674023.SomeObject) ((java.util.List) destination.getSomeObjects()).get(0)) == null))) {
//            ((Question41674023.SomeObject) ((java.util.List) destination.getSomeObjects()).get(0)).setStringSomeObject(null);
//        }
//    }
}
