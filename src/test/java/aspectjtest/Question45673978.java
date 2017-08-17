package aspectjtest;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.TypeVariableSource;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterList;
import net.bytebuddy.description.modifier.*;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static javafx.scene.input.KeyCode.M;
import static org.eclipse.jdt.internal.compiler.parser.Parser.name;

public class Question45673978 {

    @Test
    public void should() {
        Testee45673978 testee = new Testee45673978();
        testee.doSomething("123", "foo 123");
        testee.doSomething("123", "foo 123", "34534");
        testee.doSomething("foo bar");
    }

    @Test
    public void setValue() {
        Testee45673978 testee = new Testee45673978();
        testee.setValueTo1();
        testee.setValueTo2();
    }

    @Test
    public void byteBuddy() throws Exception{
        new ByteBuddy()
                .subclass(Testee45673978.class)
                .implement(ValueMixin.class)
                .defineMethod("getValue", String.class)
                .intercept(MethodDelegation.to(Testee45673978.class).andThen(FieldAccessor.ofField("value")))
                .make()
                .load(Testee45673978.class.getClassLoader())
                .getLoaded();

        Testee45673978 testee = new Testee45673978();
        testee.setValueTo1();
        System.out.println("From getValue(): " + ((ValueMixin)testee).getValue());
        testee.setValueTo2();
        System.out.println("From getValue(): " + ((ValueMixin)testee).getValue());


    }


}
