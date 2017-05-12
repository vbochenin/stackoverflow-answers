package stream;

import one.util.streamex.StreamEx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Question42221310 {

    @Test
    public void test() {
        List<MyObj> objs = Arrays.asList(
                new MyObj("some"),
                new MyObj("foo"),
                new MyObj("bar"),
                new MyObj("xyz")
        );

        List<MyObj> myObjs = StreamEx.of(objs)
                .groupRuns((myObj, myObj2) -> !"foo".equals(myObj.getProp()))
                .findFirst()
                .orElseGet(ArrayList<MyObj>::new);

        System.out.println("1. Found: " + myObjs);

        objs = Arrays.asList(
                new MyObj("foo"),
                new MyObj("some"),
                new MyObj("bar"),
                new MyObj("xyz")
        );

        myObjs = StreamEx.of(objs)
                .groupRuns((myObj, myObj2) -> !"foo".equals(myObj.getProp()))
                .findFirst()
                .orElseGet(ArrayList<MyObj>::new);

        System.out.println("2. Found: " + myObjs);

        objs = Arrays.asList(
                new MyObj("some"),
                new MyObj("bar"),
                new MyObj("xyz"),
                new MyObj("foo")
        );


        List<List<MyObj>> collect = StreamEx.of(objs)
                .groupRuns((myObj, myObj2) -> !"foo".equals(myObj.getProp()))
                .collect(Collectors.toList());

        myObjs = StreamEx.of(objs)
                .groupRuns((myObj, myObj2) -> !"foo".equals(myObj.getProp()))

                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .orElseGet(ArrayList::new);


        System.out.println("3. Found: " + myObjs);

        objs = Arrays.asList(
                new MyObj("some"),
                new MyObj("bar"),
                new MyObj("xyz")
        );

        myObjs = StreamEx.of(objs)
                .groupRuns((myObj, myObj2) -> !"foo".equals(myObj.getProp()))
                .findFirst()
                .orElseGet(ArrayList<MyObj>::new);

        System.out.println("4. Found: " + myObjs);
        List<Field<Object>> l = new ArrayList<>();
        Field<String> f = new Field<>();
        f.setEventField("Hi");


    }

    private class MyObj {
        private final String prop;

        public MyObj(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }

        @Override
        public String toString() {
            return "MyObj{" +
                    "prop='" + prop + '\'' +
                    '}';
        }
    }

    public class Field<T> {
        private T eventField;

        /**
         * @return the eventField
         */
        public T getEventField() {
            return eventField;
        }

        /**
         * @param eventField the eventField to set
         */
        public void setEventField(T eventField) {
            this.eventField = eventField;
        }
    }

    private static abstract class AbstractSample<T extends AbstractSample.SampleBuilder> {

        public abstract T copyBuilder();

        public static abstract class SampleBuilder<T extends AbstractSample, B extends SampleBuilder<T, B>> {
            abstract B with();

            abstract T build();
        }
    }

    public static class Sample1<T extends AbstractSample.SampleBuilder> extends AbstractSample<T> {

        @Override
        public T copyBuilder() {
            return null;
//            return new Builder();
        }

        public static class Builder extends AbstractSample.SampleBuilder<Sample1, Builder> {
            @Override
            Builder with() {
                return null;
            }

            @Override
            Sample1 build() {
                return null;
            }
        }
    }

    public static class Sample2 extends Sample1<Sample2.Builder> {

        @Override
        public Sample2.Builder copyBuilder() {
            return new Sample2.Builder();
        }

        public static class Builder extends AbstractSample.SampleBuilder<Sample2, Builder> {
            @Override
            Builder with() {
                return null;
            }

            @Override
            Sample2 build() {
                return null;
            }
        }
    }
}
