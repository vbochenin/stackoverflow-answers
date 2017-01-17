import static javafx.scene.input.KeyCode.T;

public class Question41692220 {

    abstract class MainClass<T extends BaseClass>{
        abstract Class<T> onCreateClass();
    }

    class ChildClass extends MainClass{
        @Override
        Class<AnotherClass> onCreateClass() {
            return AnotherClass.class;
        }
    }

    class AnotherClass extends BaseClass {

    }

    class YetAnotherChildClass<T extends BaseClass> extends MainClass<T> {

        @Override
        Class<T> onCreateClass() {
            return null;
        }
    }

    private class BaseClass {
    }


    class OneChildClass extends MainClass{
        @Override
        Class<OneAnotherClass> onCreateClass() {
            return OneAnotherClass.class;
        }
    }

    private class OneAnotherClass {
    }
}
