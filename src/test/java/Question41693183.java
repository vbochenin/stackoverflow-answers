import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Doubles;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static org.apache.coyote.http11.Constants.a;

public class Question41693183 {

    @Test
    public void testRegExp() {
        String regex = "(?ums)^[a-zA-Z0-9]+ [^ ]+$";
        Assert.assertTrue("iuy98 ÄÖ\r\nÜäöüß7".matches(regex));
        Assert.assertTrue("iuy 98ÄÖÜäöüß7*&^&%*$&".matches(regex));
        Assert.assertFalse("iuy98Ä ÖÜäöü\r\nß7*&^&%*$&".matches(regex));
        Assert.assertFalse("i u y98ÄÖÜäöüß7*&^&%*$&".matches(regex));
        Assert.assertFalse("  iuy98ÄÖÜäöüß7*&^&%*$&\r\n".matches(regex));
        Assert.assertFalse("00uy98ÄÖÜä öüß7*&^&%*$&".matches(regex));
        Assert.assertFalse("00uy98ÄÖÜä öüß7*&^&%*$&".matches(regex));
        Assert.assertFalse("$%^&00uy98ÄÖÜä öüß7*&^&%*$&".matches(regex));
        Assert.assertTrue("00uy98 ÄÖÜ____äöüß\r\n7*&^&%*$&".matches(regex));
        Assert.assertTrue("00uy98 ÄÖÜ____äöüß\r\n7*&^&%*$&".matches(regex));
        Assert.assertTrue("00 uy98{}[]ÄÖÜ____äöüß\r\n7*&^&%*$&".matches(regex));
    }

    public class Tuple implements Comparable<Tuple>{

        private double token;
        private int docID;



        public Tuple(double token, int docID) {
            this.token = token;
            this.docID = docID;

        }

        public String getToken() {
            return String.valueOf(token);
        }
        public void setToken(double token) {
            this.token = token;
        }
        public int getDocID() {
            return docID;
        }
        public void setDocID(int docID) {
            this.docID = docID;
        }

        @Override
        public int compareTo(Tuple o) {
            return Double.compare(this.token, o.token);
        }
    }


    public static void main(String args[]) {
        int number1 = 5;
        new HashSet().add(a);
        for (int number2 = 0; number2 <= number1; number2++) {
            for (int number3 = 0; number3 <= number1-number2; number3++)
            {

                char letter1 = (char)(number3 + 'A');
                System.out.print(letter1);
            }
            System.out.println();
        }
    }
}


