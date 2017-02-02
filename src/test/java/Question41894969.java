import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Question41894969 {

    Random random = new Random();

    @Test
    public void testBFSearch() {
        for (int i=0; i<100; i++) {
            System.out.println(String.format("%s", BFSearch()));
        }
    }

    public State BFSearch() {
        State initial = new State(1);
        LinkedList<State> frontier = new LinkedList<>();
        frontier.add(initial);


        while (!frontier.isEmpty()) {
            State currentState = frontier.removeFirst();

            if (goalTest(currentState)) {
                System.out.println("goal");
                return currentState;
            } else {
                for (Point a : actions(currentState)) {
                    State res = result(currentState, a);
                    if (!frontier.contains(res)) {
                        frontier.add(res);
                    }
                }

            }
        }
        return null;
    }

    private State result(State currentState, Point a) {
        if (random.nextInt(4) % 4 == 0) {
            return new State(currentState.i / 10 + a.i);
        }
        return new State(currentState.i * 10 + a.i);

    }

    private List<Point> actions(State currentState) {

        int pointsCount = random.nextInt(10);
        List<Point> points = new ArrayList<>(pointsCount);
        for (int i = 0; i < pointsCount; i++) {
            points.add(new Point(i));
        }

        return points;
    }

    private boolean goalTest(State currentState) {
        return String.valueOf(currentState.i).length() == 4;
    }


    private class State {
        private int i;

        public State(int i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            return i == state.i;

        }

        @Override
        public int hashCode() {
            return i;
        }

        @Override
        public String toString() {
            return "State{" +
                    "i=" + i +
                    '}';
        }
    }

    private class Point {
        private int i;

        public Point(int i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            return i == state.i;

        }

        @Override
        public int hashCode() {
            return i;
        }
    }
}
