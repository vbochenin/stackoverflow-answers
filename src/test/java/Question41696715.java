import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Question41696715 {

    @Test
    public void testDate() {
        String date = "01/08/2017";

        Date todaydate = new Date();

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date expiry = myFormat.parse(date);

            if(expiry.compareTo(todaydate) >0){
                System.out.println("false");
            }

            else{
                System.out.println("true");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
