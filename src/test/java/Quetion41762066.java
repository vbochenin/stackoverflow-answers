import com.google.common.collect.ImmutableSet;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

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

    public static final String APP_B_TIMESTAMP_FORMAT = "uuuuMMdd HH:mm:ss.SS";
    public static final ZoneId CENTRAL_EUROPEAN_TIME_ZONE = ZoneId.of("CET");
    public static final ZoneId GREENWICH_MEAN_TIME_ZONE = ZoneId.of("GMT");


    public Timestamp timestamp(String timestamp) {

        return Timestamp.valueOf(
                ZonedDateTime
                        .parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZone(CENTRAL_EUROPEAN_TIME_ZONE))
                        .withZoneSameInstant(GREENWICH_MEAN_TIME_ZONE)
                        .toLocalDateTime()
        );
    }



    @Test
    public void testDigits() {
        int decNum = 221;

        int division = decNum;
        String allDigits = "";

        //Calculate the digitZero
        allDigits += setDigit(division);
        //Calculate the other digits
        do{
            division = division / 16;
            allDigits+=setDigit(division);
        } while (division >= 16);

        //Reverse the string into a normal hexadecimal view
        allDigits = new StringBuilder(allDigits).reverse().toString();
        //Display the result
        System.out.println("" + allDigits);
    }


    public static String setDigit(int Number) {
        int division = Number;
        int digit = division % 16;
        String hexDigits = "";
        if (digit > 9) {
            switch (digit) {
                case 10:
                    hexDigits += "A";
                    break;
                case 11:
                    hexDigits += "B";
                    break;
                case 12:
                    hexDigits += "C";
                    break;
                case 13:
                    hexDigits += "D";
                    break;
                case 14:
                    hexDigits += "E";
                    break;
                case 15:
                    hexDigits += "F";
                    break;

            }
        } else {
            hexDigits += Integer.toString(digit);
        }
        return hexDigits;
    }
}
