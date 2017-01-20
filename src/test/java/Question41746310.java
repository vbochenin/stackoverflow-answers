import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;

public class Question41746310 {

    public abstract class BaseClass<T extends FirstClass, U extends BaseAnother<? extends SecondClass>> {
    }

    private class FirstClass {
    }

    private class FirstClassImpl extends FirstClass {

    }

    private class SecondClass {
    }

    private class BaseAnother<T extends SecondClass> {
    }

    private class AnotherSecondClass extends SecondClass {
    }

    private class YetAnotherSecondClass extends SecondClass {
    }

    private class BaseAnotherImpl extends BaseAnother<AnotherSecondClass> {
    }

    public  class BaseClassImpl extends BaseClass<FirstClassImpl, BaseAnotherImpl> {

    }

    public  class AnotherBaseClassImpl extends BaseClass<FirstClassImpl, BaseAnother<YetAnotherSecondClass>> {

    }

   public interface Student extends Comparable<Student> {
       int percentage();
   }


}
