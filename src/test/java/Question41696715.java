import org.hamcrest.Matchers;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;

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
        HashMap<String, String> str = new HashMap<>();
        str.put("a", "asss");
        str.entrySet();
    }

    public class MyHashMap<K, V> extends HashMap {
        @Override
        public Boolean put(Object key, Object value) {
            try {
                super.put(key, value);
            } catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e) {
                return false; // or do what you want!
            }
            return true;
        }
    }

    @Test
    public void testExecutors() {
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            FutureTask<Boolean> task = new FutureTask<>(() -> true);
            Future<?> submit = service.submit(task);
            System.out.println(task.get());
            System.out.println(service.submit(() -> true).get());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
    }
}
