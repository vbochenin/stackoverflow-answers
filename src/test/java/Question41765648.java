import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.Vanilla.std;

/**
 * Jackson - Deserialize Interface to enum
 */
public class Question41765648 {

    public interface Event {
    }

    public static class Input
    {
        private Event event;

        @JsonDeserialize(using = UserEventDeserializer.class)
        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }
    }

    public enum UserEvent implements Event
    {
        SIGNUP;
    }

    public enum BusinessEvent implements Event
    {
        BUSINESS_SIGNUP;
    }

    public static class UserEventDeserializer  extends StdDeserializer<Event> {

        protected UserEventDeserializer() {
            super(Event.class);
        }

        @Override
        public Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return UserEvent.valueOf(p.getText());
        }
    }

    @Test
    public void converEnum() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue("{\n" +
                "  \"event\" : \"SIGNUP\"\n" +
                "}", Input.class);

        Assert.assertThat(input.getEvent(), Matchers.is(UserEvent.SIGNUP));
    }
}
