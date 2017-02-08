package reflection;

public class TestClass {
    String fileName;

    public TestClass(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
