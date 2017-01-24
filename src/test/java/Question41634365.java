import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Question41634365 {
    BaseManager baseManager = new BaseManager();


    public void convertItemsToJSon(List<Item> items) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<JsonObjectBuilder> groupsBuilder = new ArrayList<>();

        Map<Item, List<Class2>> sqlItems = items
                .stream()
                .collect(Collectors.toMap(Function.identity(), (item) -> baseManager.findObjectsByNamedQuery(Class2.class, "Class2.findByCratedBy", new Object[]{item.getCreatedBy()})));

        sqlItems.entrySet()
                .stream()
                .map(sqlItem -> buildJson(sqlItem.getKey(), sqlItem.getValue()))
                .forEach(groupsBuilder::add);
    }

    public void convertItemsToJSonStreamEx(List<Item> items) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<JsonObjectBuilder> groupsBuilder = new ArrayList<>();

        StreamEx.of(items)
                .cross(item -> baseManager.findObjectsByNamedQuery(Class2.class, "Class2.findByCratedBy", new Object[]{item.getCreatedBy()}).stream())
                .mapKeys(item -> Json.createObjectBuilder().add("createdBy", item.getCreatedBy()))
                .mapKeyValue(this::addField)
                .forEach(groupsBuilder::add);


    }

    private JsonObjectBuilder addField(JsonObjectBuilder json, Class2 class2) {
        // You logic how to convert class2 to field in JSON
        return json;
    }

    private JsonObjectBuilder buildJson(Item item, List<Class2> class2Items) {
        return Json.createObjectBuilder().add("createdBy", item.getCreatedBy());
    }

    private class Item {
        private Object createdBy;

        public Object getCreatedBy() {
            return createdBy;
        }
    }

    private class Class2 {
    }

    private class BaseManager {
        public List<Class2> findObjectsByNamedQuery(Class<Class2> class2Class, String s, Object[] objects) {
            return null;
        }
    }

    private static class JsonObjectBuilder {
        public JsonObjectBuilder add(String key, Object value) {
            return this;
        }
    }

    private static class Json {
        static JsonObjectBuilder createObjectBuilder() {
            return new JsonObjectBuilder();
        }
    }
}
