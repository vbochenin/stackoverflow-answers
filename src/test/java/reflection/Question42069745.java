package reflection;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question42069745 {

    public void insertData(String className, String fileName) {
        List newList = new ArrayList();

        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object a1 = getSomeObjectFromSomewhere();

        if (clazz.isInstance(a1)) {
            newList.add(a1);
        }
    }

    public TestClass createTestClass(String fileName) {
        return new TestClass(fileName);
    }

    public OtherClass createOtherClass(String fileName) {
        return new OtherClass(fileName);
    }

    @Test
    public void testInsertDataForClassName() {
//        System.out.println(insertData("reflection.TestClass", "fileName"));
//        System.out.println(insertData("reflection.OtherClass", "fileName"));

    }

    public Object getSomeObjectFromSomewhere() {
        return null;
    }
}
