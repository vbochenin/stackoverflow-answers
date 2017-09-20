import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecodeMapTest {

    Pattern keyValuePattern  = Pattern.compile("(?ms)([^=]*)=([^=]*)");

    @Test
    public void shouldReturnProperMap() {
        System.out.println("0." + decode("123=fdr&123n=&opiu="));
        System.out.println("1." + decode("asdf=123&123=fdr&123=&opiu="));
        System.out.println("2." + decode("asdf=123"));
        System.out.println("3." + decode("&&&&&&&&&&&&"));
        System.out.println("4." + decode("asdf=\r\n4234290678&lskdfj\r\n=&\r\n=\r\n"));
    }

    private Map<String, String> decode(String str) {
        if (str == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        if ("".equals(str)) {
            return result;
        }

        if (!str.contains("&") && !str.contains("=")) {
            throw new IllegalArgumentException("Input string doesn't contains pair separator (&) and key-value separator (=)");
        }

        //todo check case with '&&&&&'
        String[] keyValuePairs = str.split("&");

        for (String keyValuePair : keyValuePairs) {
            Matcher m = keyValuePattern.matcher(keyValuePair);
            if (!m.matches()) {
                throw new IllegalArgumentException(String.format("Invalid format of key-value pair %s. Should be 'key=value'", keyValuePair));
            }
            result.put(m.group(1), m.group(2));
        }

        return result;

    }
}
