import com.google.common.collect.ImmutableSet;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.addAll;

public class Quetion41762066 {

    private Map<String, Boolean> printed = new HashMap<>();

    public void printSubs(String str) {
        if (printed.containsKey(str)) {
            return;
        }
        printed.put(str, true);
        System.out.print(str + " ");

        if (str.length() > 1) {
            printSubs(str.substring(0, 1));
            printSubs(str.substring(1));

            if (str.length() > 2) {
                printSubs(str.substring(0, 1) + str.substring(2));
                printSubs(str.substring(0, str.length()-1));
            }
        }
    }

    public Set<String> returnSubs(String str) {
        Set<String> subs = new HashSet<>();
        subs.add(str);
        if (str.length() > 1) {
            subs.addAll(returnSubs(str.substring(0, 1)));
            subs.addAll(returnSubs(str.substring(1)));
            if (str.length() > 2) {
                Set<String> subSubs = returnSubs(str.substring(2));
//                subSubs.addAll(returnSubs(str.substring(0, str.length()-1)));
                subs.addAll(subSubs);
                for (String subsub : subSubs) {
                    subs.addAll(returnSubs(str.substring(0, 1) + subsub));
                }
            }
        }
        return subs;
    }

    @Test
    public void testPrintSubs() {
        printSubs("home");
        System.out.println();
        for (String str : returnSubs("home")) {
            System.out.print(str + " ");
            
        }
    }
}
