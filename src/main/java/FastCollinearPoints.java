import java.util.Arrays;

public class FastCollinearPoints {

    private final Point[] points;

    private LineSegment[] lineSegments;

    private int segmentCount = 0;

    public FastCollinearPoints(Point[] points) {
        checkNulls(points);
        Point[] sorted = new Point[points.length];
        System.arraycopy(points, 0, sorted, 0, points.length);
        Arrays.sort(sorted);
        this.points = sorted;
        this.lineSegments = new LineSegment[points.length];
        checkDuplicates();
        calculateSegments();
    }

    private void calculateSegments() {
        SlopeHolder holder = new SlopeHolder();

        for (Point point : points) {
            Point[] sorted = new Point[points.length];
            System.arraycopy(points, 0, sorted, 0, points.length);
            Arrays.sort(sorted, point.slopeOrder());
            int count = 1;
            Point current = sorted[0];
            double currentSlope = point.slopeTo(current);
            for (int i = 1; i < sorted.length; i++) {
                double newSlope = point.slopeTo(sorted[i]);
                if (isEquals(currentSlope, newSlope)) {
                    count++;
                } else {
                    if (isNewLineSegment(holder, count, currentSlope)) {
                        lineSegments[segmentCount++] = new LineSegment(point, sorted[i - 1]);
                    }
                    count = 1;
                    currentSlope = newSlope;
                }
            }
            if (isNewLineSegment(holder, count, currentSlope)) {
                lineSegments[segmentCount++] = new LineSegment(point, sorted[sorted.length - 1]);
            }
        }

        LineSegment[] segments = new LineSegment[segmentCount];
        System.arraycopy(lineSegments, 0, segments, 0, segmentCount);
        lineSegments = segments;
    }

    private boolean isNewLineSegment(SlopeHolder holder, int count, double currentSlope) {
        return (count >= 3) && (holder.putIfAbsent(currentSlope) == null);
    }

    private static class SlopeHolder {
        private double[] slopes = new double[0];

        private Double putIfAbsent(double slope) {
            Double found = null;
            for (double s : slopes) {
                if (Double.compare(s, slope) == 0) {
                    found = s;
                    break;
                }
            }
            if (found == null) {
                double[] newSlopes = new double[slopes.length + 1];
                System.arraycopy(slopes, 0, newSlopes, 0, slopes.length);
                newSlopes[newSlopes.length - 1] = slope;
                slopes = newSlopes;
            }
            return found;
        }
    }

    private boolean isEquals(double s1, double s2) {
        return new Double(s1).equals(s2);
    }

    private void checkDuplicates() {
        if (points.length < 2) {
            return;
        }
        Point current = points[0];
        for (int i = 1; i < points.length; i++) {
            if (current.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException("!");
            }
            current = points[i];
        }
    }

    private void checkNulls(Point[] input) {
        if (input == null) {
            throw new NullPointerException("!");
        }
        for (Point point : input) {
            if (point == null) {
                throw new NullPointerException("!");
            }
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentCount];
        System.arraycopy(lineSegments, 0, segments, 0, segmentCount);
        return segments;
    }

    public static void main(String... args) {
        FastCollinearPoints p = new FastCollinearPoints(new Point[]{
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000),
        });


        p.segments();
    }

}
