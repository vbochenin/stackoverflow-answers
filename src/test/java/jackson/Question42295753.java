package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import reactor.TestReactor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Predicate;

public class Question42295753 {

    private final static String JSON = "[\n" +
            "  {\n" +
            "    \"Event\": \"Wedding\",\n" +
            "    \"Studios\": [\n" +
            "      \"Studio 1\",\"Studio 2\"\n" +
            "    ],\n" +
            "    \"Location\": [\"Ernakulam\",\"Bangalore\"]\n" +
            "  },\n" +
            "  {\n" +
            "    \"Event\": \"Birthday\",\n" +
            "    \"Studios\": [\n" +
            "      \"Studio 1\"\n" +
            "    ],\n" +
            "    \"Location\": [\"Ernakulam\"]\n" +
            "\n" +
            "  },\n" +
            "  {\n" +
            "    \"Event\": \"Engagement\",\n" +
            "    \"Studios\": [\n" +
            "       \"Studio 1\",\"Studio 2\",\"Studio 2\"\n" +
            "    ],\n" +
            "    \"Location\": [\"Ernakulam\",\"Bangalore\",\"Angamaly\"]\n" +
            "  }\n" +
            "]";

    @Test
    public void test() throws Exception {
        SimpleModule sm = new SimpleModule();
        sm.addDeserializer(Events.class, new EventsDeserializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(sm);

        Events events = mapper.readValue(JSON, Events.class);
        System.out.println(events);

        Event[] jsonEvent = mapper.readValue(JSON, Event[].class);
        System.out.println(events);
    }

    public static class Event {
        @JsonProperty("Event")
        private String event;
        @JsonProperty("Studios")
        private List<String> bid;
        @JsonProperty("Location")
        private List<String> location;

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }
    }


    public static class Events {
        private final String[] event;
        private final String[][] bid;
        private final String[][] location;

        public Events(String[] event, String[][] bid, String[][] location) {
            this.event = event;
            this.bid = bid;
            this.location = location;
        }

        public String[] getEvent() {
            return event;
        }

        public String[][] getBid() {
            return bid;
        }

        public String[][] getLocation() {
            return location;
        }
    }

    private class EventsDeserializer extends StdDeserializer<Events> {
        protected EventsDeserializer() {
            super(Events.class);
        }

        @Override
        public Events deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ArrayNode arrayNode = p.readValueAsTree();

            String[] events = new String[arrayNode.size()];
            String[][] bid = new String[arrayNode.size()][0];
            String[][] location = new String[arrayNode.size()][0];

            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode internalNode = arrayNode.get(i);
                events[i] = internalNode.get("Event").asText();
                bid[i] = convertToArray(internalNode.get("Studios"));
                location[i] = convertToArray(internalNode.get("Location"));
            }

            return new Events(events, bid, location);
        }

        private String[] convertToArray(JsonNode childNode) {
            String[] locations = new String[0];
            if (childNode != null && childNode.isArray()) {
                ArrayNode locationNodeArrayNode = (ArrayNode) childNode;
                locations = new String[locationNodeArrayNode.size()];
                for (int j = 0; j < locations.length; j++) {
                    locations[j] = locationNodeArrayNode.get(j).asText();
                }
            }
            return locations;
        }
    }

    @Test
    public void testSearchNode() throws Exception {
        String json = "{\n" +
                "\t\"name\": \"NODE1\",\n" +
                "\t\"frames\": [{\n" +
                "\t\t\"name\": \"NODE2\",\n" +
                "\t\t\"frames\": []\n" +
                "\t}, {\n" +
                "\t\t\"name\": \"NODE3\",\n" +
                "\t\t\"frames\": [{\n" +
                "\t\t\t\"name\": \"NODE5\",\n" +
                "\t\t\t\"frames\": []\n" +
                "\t\t}, {\n" +
                "\t\t\t\"name\": \"NODE6\",\n" +
                "\t\t\t\"frames\": [{\n" +
                "\t\t\t\t\"name\": \"NODE7\",\n" +
                "\t\t\t\t\"frames\": []\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"name\": \"NODE4\",\n" +
                "\t\t\"frames\": []\n" +
                "\t}]\n" +
                "}";

        Stack<JSONObject> chain = new Stack<>();
        searchNode(chain, new JSONObject(json), (node) -> {
            try {
                return "NODE7".equals(node.get("name"));
            } catch (Exception e) {
                return false;
            }
        });
        System.out.println(new ArrayList<>(chain));
    }

    private static boolean searchNode(Stack<JSONObject> chain,
                                      JSONObject currentNode, Predicate<JSONObject> condition) throws Exception {

        if (condition.test(currentNode)) {
            chain.add(currentNode);
            return true;
        }

        JSONArray children = currentNode.getJSONArray("frames");
        if (children == null) {
            return false;
        }

        for (int i = 0; i < children.length(); i++) {
            if (searchNode(chain, children.getJSONObject(i), condition)) {
                chain.add(currentNode);
                return true;
            }
        }

        return false;
    }
}
