package reflection;

public class OtherClass {
    String fileName;

    public OtherClass(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "OtherClass{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
