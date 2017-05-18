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
        return Math.abs(s1 - s2) < 0.0000000001;
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
        return lineSegments;
    }
}
