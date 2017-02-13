package reflection;

import com.google.common.collect.Multimaps;
import org.springframework.util.ReflectionUtils;

import java.util.*;

import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.T;

public class Question42207453 {

    interface A {}
    interface B extends A {}
    interface C extends A {}

    class D implements B {}
    class E implements C {}

    public Question42207453() {
        Map<Class<? extends A>, A> map = new HashMap<>();

        A b = new B(){};
        A d = new D();
        A e = new E();

        map.put(B.class, b);
        map.put(d.getClass(), d);

        map.put(e.getClass(), e);

        System.out.println(B.class.getSimpleName() + ": " + getMapEntries(map, B.class));
        System.out.println(C.class.getSimpleName() + ": " + getMapEntries(map, C.class));

        ClassHierarchyHashMap hierarchyMap = new ClassHierarchyHashMap();
        hierarchyMap.put(d.getClass(), d);
        hierarchyMap.put(B.class, b);
        hierarchyMap.put(e.getClass(), e);

        System.out.println(B.class.getSimpleName() + ": " + hierarchyMap.getAllValuesInHierarchy(B.class));
        System.out.println(D.class.getSimpleName() + ": " + hierarchyMap.getAllValuesInHierarchy(D.class));
        System.out.println(C.class.getSimpleName() + ": " + hierarchyMap.getAllValuesInHierarchy(C.class));
        System.out.println(A.class.getSimpleName() + ": " + hierarchyMap.getAllValuesInHierarchy(A.class));
    }

    private static class ClassHierarchyHashMap extends HashMap<Class<? extends A>, A> {
        private final Map<Class<? extends A>, Set<Class<? extends A>>> hierarchyMap = new HashMap<>();

        @Override
        public A put(Class<? extends A> key, A value) {
            calcHierarchyFor(key);
            return super.put(key, value);
        }

        private void calcHierarchyFor(Class<? extends A> key) {
            if (hierarchyMap.containsKey(key)) {
                return;
            }
            Set<Class<? extends A>> assignableClasses = new HashSet<>();

            assignableClasses.addAll(getSuperClasses(key));
            assignableClasses.addAll(getInterfaces(key));
            for (Class<? extends A> assignableClass : assignableClasses) {
                calcHierarchyFor(assignableClass);
            }

            assignableClasses.add(key);
            hierarchyMap.put(key, assignableClasses);
        }

        public List<A> getAllValuesInHierarchy(Class<? extends A> key) {
            List<A> result = new ArrayList<>();
            Set<Class<? extends A>> hierarchy = hierarchyMap.get(key);
            if (hierarchy == null) {
                return result;
            }
            for (Class<? extends A> clazz : hierarchy) {
                A value = this.get(clazz);
                if (value != null) {
                    result.add(value);
                }
            }

            return result;
        }

        private Set<Class<? extends A>> getInterfaces(Class<?> key) {
            Set<Class<? extends A>> result = new HashSet<>();
            Class<?>[] interfaces = key.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (A.class.isAssignableFrom(anInterface)) {
                    result.add((Class<? extends A>) anInterface);
                    result.addAll(getInterfaces(anInterface));
                }
            }
            return result;
        }

        private Set<Class<? extends A>> getSuperClasses(Class<?> key) {
            Set<Class<? extends A>> result = new HashSet<>();

            Class<?> superClass = key.getSuperclass();
            while (superClass != null && A.class.isAssignableFrom(superClass)) {
                result.add((Class<? extends A>) superClass);
                superClass = superClass.getSuperclass();
            }
            return result;
        }
     }


    private <T extends A> List<T> getMapEntries(Map<Class<? extends A>, A> map, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Map.Entry<Class<? extends A>, A> entry : map.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                result.add((T) entry.getValue());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        new Question42207453();
    }
}
