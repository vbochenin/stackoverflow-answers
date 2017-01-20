import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Question41760662 {

    @Test
    public void compareJodaTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String today = "2017-01-20 14:51:30.091";  // i get this from service call in this format
        String tomorrow = "2017-01-21 14:51:30.091";  // i get this from service call in this format

        DateTime now = DateTime.now();

        Assert.assertThat(now.toLocalDate().compareTo(DateTime.parse(today, dateTimeFormatter).toLocalDate()), Matchers.is(0));
        Assert.assertThat(now.toLocalDate().compareTo(DateTime.parse(tomorrow, dateTimeFormatter).toLocalDate()), Matchers.is(-1));
        Assert.assertThat(now.toLocalDate().isEqual(DateTime.parse(today, dateTimeFormatter).toLocalDate()), Matchers.is(true));
        Assert.assertThat(now.toLocalDate().isBefore(DateTime.parse(tomorrow, dateTimeFormatter).toLocalDate()), Matchers.is(true));


        Assert.assertThat(DateTimeComparator.getDateOnlyInstance().compare(now, DateTime.parse(today, dateTimeFormatter)), Matchers.is(0));
        Assert.assertThat(DateTimeComparator.getDateOnlyInstance().compare(now, DateTime.parse(tomorrow, dateTimeFormatter)), Matchers.is(-1));
        Assert.assertThat(DateTimeComparator.getDateOnlyInstance().compare(now, DateTime.parse(tomorrow, dateTimeFormatter)), Matchers.is(-1));
    }
}
