package javaint;

import org.junit.Test;

import java.util.Objects;

public class DeclaredMethod {

    public static class A {
        Object getA() {
            return new Object();
        }
        Object getB() {
            return new Object();
        }
    }

    public static class B extends A {
        @Override
        String getA() {
            return "";
        }


    }

    @Test
    public void test() {
        B.class.getDeclaredMethods();
    }

}
