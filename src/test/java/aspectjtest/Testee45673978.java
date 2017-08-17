package aspectjtest;

public class Testee45673978 {

    private String value;

    public void doSomething(@ReplaceFooBar String myString) {
        System.out.println(myString);
    }

    public void doSomething(String arg, @ReplaceFooBar String myString) {
        System.out.println(arg + " " + myString);
    }

    public void doSomething(String arg, @ReplaceFooBar String myString, String arg1 ) {
        System.out.println(arg + " " +myString + " " + arg1);
    }

    public void setValueTo1() {
        value = "1";
    }

    public void setValueTo2() {
        value = "2";
    }

}
