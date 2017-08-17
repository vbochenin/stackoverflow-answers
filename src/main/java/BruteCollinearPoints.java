import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;

    private LineSegment[] lineSegments;

    private int segmentCount = 0;

    public BruteCollinearPoints(Point[] points) {
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
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                double slopePq = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length; r++) {
                    double slopePr = points[p].slopeTo(points[r]);

                    for (int s = r + 1; s < points.length; s++) {
                        double slopePs = points[p].slopeTo(points[s]);
                        if (isEquals(slopePq, slopePr) && isEquals(slopePq, slopePs)) {
                            lineSegments[segmentCount++] = new LineSegment(points[p], points[s]);
                        }
                    }
                }
            }
        }

        LineSegment[] segments = new LineSegment[segmentCount];
        System.arraycopy(lineSegments, 0, segments, 0, segmentCount);
        lineSegments = segments;

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

    private void checkNulls(Point[] points) {
        if (points == null) {
            throw new NullPointerException("!");
        }
        for (Point point : points) {
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
        //3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000

        BruteCollinearPoints p = new BruteCollinearPoints(new Point[]{
                new Point(13232, 5811),
                new Point(22833, 7661),
                new Point(22280, 31792),
                new Point(11482, 4999),
                new Point(22280, 31792)});


        p.segments();
    }
}
