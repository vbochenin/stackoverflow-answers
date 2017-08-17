import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class Question45568123 {

    @Test
    public void replaceSymbol() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


        Assert.assertEquals("09-08-2017", sdf.format(new Date()));


    }

    private String formatStreet(String streetName) {
        String result = streetName;
        Pattern pattern = Pattern.compile(" [dD]'([a-z])");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            result = matcher.replaceFirst(" d'" + matcher.group(1).toUpperCase());
            matcher = pattern.matcher(result);
        }

        return result;
    }

}
