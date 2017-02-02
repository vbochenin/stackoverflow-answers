import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import one.util.streamex.StreamEx;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<String, Integer> messageListeners = new HashMap<>();
        messageListeners.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(Object::toString)
                .collect(Collectors.joining(""))

        ;

    }


    @Test
    public void testJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Banner banner = objectMapper.readValue("{\n" +
                "    \"idClick\": 3,\n" +
                "    \"banner\":\n" +
                "    {\n" +
                "        \"idBanner\": 1,\n" +
                "        \"clickSet\": null,\n" +
                "        \"businesscentr\":\n" +
                "        {\n" +
                "            \"idBc\": 1,\n" +
                "            \"bannerSet\": null,\n" +
                "            \"email\": \"new@mail.ru\",\n" +
                "            \"telephoneBc\": \"79220909777\"\n" +
                "        },\n" +
                "        \"textBanner\": \"onebanner\",\n" +
                "        \"filepathBanner\": \"banner\\banner.jpg\",\n" +
                "        \"timeStart\": 1580210257811,\n" +
                "        \"timeEnd\": 1485602255190\n" +
                "    },\n" +
                "    \"fullnameClient\": \"voav\"\n" +
                "}", Banner.class
        );

        System.out.println(banner);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Banner {

        @JacksonInject
        private Integer bannerId;

        @JsonProperty("fullnameClient")
        private String fullName;

        public Integer getBannerId() {
            return bannerId;
        }

        public void setBannerId(Integer bannerId) {
            this.bannerId = bannerId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

}
