package mock;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Question45753122 {

    @InjectMocks
    A a;

    private <T extends A> void doSomething(List<T> list, Class<T> clazz) {

    }

    @Test
    public void setup() {
        Car car = new Car();
        car.versions = ImmutableList.of(
                new VersionCar("wheels1", "engine1"),
                new VersionCar("wheels2", "engine2"),
                new VersionCar("wheels3", "engine1"),
                new VersionCar("wheels1", "engine4")
        );
        Map<String, Set<String>> mapAllItems =
                car.getVersions().stream()
                        .map(versionCar -> processObjects(versionCar))
                        .flatMap(m -> m.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                new BinaryOperator<Set<String>>() {
                                    @Override
                                    public Set<String> apply(Set<String> firstSet, Set<String> secondSet) {
                                        Set<String> result = new HashSet<>();
                                        result.addAll(firstSet);
                                        result.addAll(secondSet);
                                        return result;
                                    }
                                }
                        ));

        System.out.println(mapAllItems);
    }

    private class A {

    }

    private class B extends A {

    }

    private class C extends A {

    }


    private abstract class ExecutorForA<T extends A> {
        private void doSomething(List<T> list) {

        }
    }

    private class ExecutorForB extends ExecutorForA<B> {
    }

    private class ExecutorForC extends ExecutorForA<C> {
    }

    public class Car {
        List<VersionCar> versions;

        public List<VersionCar> getVersions() {
            return versions;
        }
    }

    public class VersionCar {
        private String wheelsKey;
        private String engineKey;

        public VersionCar(String wheelsKey, String engineKey) {
            this.wheelsKey = wheelsKey;
            this.engineKey = engineKey;
        }

        public String getWheelsKey() {
            return wheelsKey;
        }

        public String getEngineKey() {
            return engineKey;
        }
    }

    private static Map<String, Set<String>> processObjects(VersionCar version) {
        Map<String, Set<String>> mapItems = new HashMap<>();
        mapItems.put("engine", new HashSet<>(Arrays.asList(version.getEngineKey())));
        mapItems.put("wheels", new HashSet<>(Arrays.asList(version.getWheelsKey())));
        return mapItems;
    }

}
