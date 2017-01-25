package reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestReactor {

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Consumer<Event> eventConsumerEven = (event) -> {
            if (event.groupId % 2 != 0) {
                System.out.println("Got odd");
                return;
            }
            System.out.println(String.format("Even: Consumed %s in Thread [%s]", event, Thread.currentThread().getName()));
        };
        Consumer<Event> eventConsumerOdd = (event) -> {
            if (event.groupId % 2 == 0) {
                System.out.println("Got even");
                return;
            }
            System.out.println(String.format("Odd: Consumed %s in Thread [%s]", event, Thread.currentThread().getName()));
        };
        Consumer<SynchronousSink<Event>> generator = eventSynchronousSink -> {
//            for (int i =0; i <100; i++) {
            int rand = random.nextInt();
            int groupId = rand % 10;
            Event event = new Event(groupId, "message for " + rand);
            System.out.println(String.format("Generated %s in Thread [%s]", event, Thread.currentThread().getName()));
            eventSynchronousSink.next(event);
//            }
        };

        Scheduler groupByScheduler = Schedulers.newParallel("groupByPool", 16);
        Scheduler subscriptionByScheduler = Schedulers.newParallel("subscriptionByPool", 16);

        final ListEventConsumer consumer = new ListEventConsumer();

//        Flux.range(0, 1000)
//                .log()
//                .groupBy(i -> i % 10)
//                .log()
//                .map(g -> g.publishOn(groupByScheduler).log("groupPublish-"+g.key()).buffer(10))
//                .subscribe(new Consumer<Flux<List<Integer>>>() {
//                               @Override
//                               public void accept(Flux<List<Integer>> listFlux) {
//                                   listFlux.subscribe(new Consumer<List<Integer>>() {
//                                       @Override
//                                       public void accept(List<Integer> integers) {
//                                           System.out.println(String.format("Got values %s by %s", integers, this));
//                                       }
//                                   });
//                               }
//                           }
//                );

//        Flux.range(0, 1000)
//                .log()
//                .groupBy(i -> i % 10)
//                .log()
//                .subscribe(g -> g.subscribe(e->String.format("Got value %s by key %s", e, g.key())));

//                .map(g -> g.publishOn(groupByScheduler).log("groupPublish-"+g.key()).buffer(10))
//                .subscribe(new Consumer<Flux<List<Integer>>>() {
//                               @Override
//                               public void accept(Flux<List<Integer>> listFlux) {
//                                   listFlux.subscribe(new Consumer<List<Integer>>() {
//                                       @Override
//                                       public void accept(List<Integer> integers) {
//                                           System.out.println(String.format("Got values %s by %s", integers, this));
//                                       }
//                                   });
//                               }
//                           }
//                );

        Flux.range(0, 1000)
                .log()
                .groupBy(i -> i % 10)
                .log()
                .map(g -> g.publishOn(groupByScheduler))
                .subscribe(
                        f -> f.subscribe((integers) -> System.out.println(String.format("Got values %s by %s", integers, Thread.currentThread().getName())))
                );
//        Flux.range(0, 1000)
//                .log()
//                .groupBy(i -> i % 10)
//                .log()
//                .map(g -> g.publishOn(groupByScheduler))
//                .subscribe(
//                        f -> f.subscribe((integers) -> System.out.println(String.format("Got values %s by %s", integers, Thread.currentThread().getName())))
//                );
//                .flatMap(
//                        stream -> {
//                            Flux<List<Event>> newStream = stream
//                                    .log("groupBy" + stream.key())
//                                    .subscribeOn(groupByScheduler)
//                                    .buffer()
////                                    .map(events1 -> {
////                                        consumer.accept(events1);
////                                        return null;
////                                    })
////                                    .publishOn(groupByScheduler)
////                                    .publishOn(groupByScheduler)
////                                    .autoConnect()
////                                    .subscribeOn(subscriptionByScheduler)
//                                    ;
//
//                            return newStream;
//
//                        }
//
//                )

        ;
//        events.subscribe(consumer);
//        events.subscribe(consumer);

//        events.subscribe(System.out::println);
        Thread.sleep(5000);


//        Cancellation c = Flux
//                .range(1, 10)
//                .groupBy( n -> n % 2 == 0)
//                .flatMap( stream -> stream
//                        .publishOn(supplier1)
//                        .log("groupBy-" + stream.key()))
//                .parallel(5)
//                .runOn(supplier2)
//                .sequential()
//                .publishOn(asyncGroup)
//                .log("join")
//                .subscribe( t -> {
//            latch.countDown();
//        });
//        flow.subscribe();
//        flow.subscribe(event -> new EventConsumer(event.groupId));

//        flow.subscribeOn(groupByScheduler).subscribe(eventConsumerEven);
//        flow.subscribe(eventConsumerOdd);

//        WorkQueueProcessor<Event> queue = WorkQueueProcessor.create();
//        queue.generate(generator).delayMillis(1000).groupBy(Event::getGroupId);
//        queue.subscribe(eventConsumerEven);
//        queue.subscribe(eventConsumerOdd);
//        queue
//                .groupBy(Event::getGroupId);
//
//        ;


//       flow.blockLast();
    }

    @Test
    public void testMessages() {
        Flux.fromStream(incomingMessages())
                .groupBy(Message::getEntityId)
                .map(g -> g.publishOn(Schedulers.newParallel("groupByPool", 16))) //create new publisher for groups of messages
                .subscribe( //create consumer for main stream
                        stream ->
                                stream.subscribe(this::processMessage) // create consumer for group stream
                );
    }

    public Stream<Message> incomingMessages() {
        return IntStream.range(0, 100).mapToObj(i -> new Message(i, i % 10));
    }

    public void processMessage(Message message) {
        System.out.println(String.format("Message: %s processed by the thread: %s", message, Thread.currentThread().getName()));
    }

    private static class Message {
        private final int id;
        private final int entityId;

        public Message(int id, int entityId) {
            this.id = id;
            this.entityId = entityId;
        }

        public int getId() {
            return id;
        }

        public int getEntityId() {
            return entityId;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "id=" + id +
                    ", entityId=" + entityId +
                    '}';
        }
    }

    public static class EventConsumer implements Consumer<Event> {
        private final int groupId;

        public EventConsumer(int groupId) {
            this.groupId = groupId;
        }

        @Override
        public void accept(Event event) {
            if (event.getGroupId() != groupId) {
                System.out.println(String.format("Wrong!!! wait %s but got %s", groupId, event));
                return;
            }
            System.out.println(String.format("Success!!! wait %s and got %s in %s", groupId, event, this));
        }
    }

    public static class ListEventConsumer implements Consumer<List<Event>> {
        @Override
        public void accept(List<Event> events) {
            Map<Integer, Event> maps = events
                    .stream()
                    .collect(Collectors.toMap(Event::getGroupId, Function.identity()));

            if (maps.size() > 1) {
                System.out.println(String.format("Wrong!!! Got %s", maps.keySet()));
                return;
            }

            for (Event event : events) {
                System.out.println(String.format("Success!!! Got %s in %s", event, this));
            }
        }
    }

    public static class Event {
        private int groupId;
        private String message;

        public Event(int groupId, String message) {
            this.groupId = groupId;
            this.message = message;
        }

        public int getGroupId() {
            return groupId;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "groupId=" + groupId +
                    ", message='" + message + '\'' +
                    '}';
        }
    }


}
