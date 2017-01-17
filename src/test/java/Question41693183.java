import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

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
}
