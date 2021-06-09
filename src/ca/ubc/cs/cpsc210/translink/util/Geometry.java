package ca.ubc.cs.cpsc210.translink.util;

/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param point     the point in question
     * @return true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {

        return (between(northWest.getLongitude(), southEast.getLongitude(), point.getLongitude()) &&
                between(southEast.getLatitude(), northWest.getLatitude(), point.getLatitude()));
    }

    /**
     * Return true if the rectangle intersects the line
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param src       one end of the line in question
     * @param dst       the other end of the line in question
     * @return true if any point on the line is on the boundary or inside the rectangle
     */
    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {

        LatLon northEast = new LatLon(northWest.getLatitude(), southEast.getLongitude());
        LatLon southWest = new LatLon(southEast.getLatitude(), northWest.getLongitude());


        // cases where line is on left side
        if (src.getLongitude() == dst.getLongitude() && src.getLongitude() == northWest.getLongitude()){

            if (between(src.getLatitude(), dst.getLatitude(), northWest.getLatitude()) ||
                    between(src.getLatitude(), dst.getLatitude(), southWest.getLatitude()))
                return true;
            if (between(dst.getLatitude(), src.getLatitude(), northWest.getLatitude()) ||
                    between(dst.getLatitude(), src.getLatitude(), southWest.getLatitude()))
                return true;


            if (between(southWest.getLatitude(), northWest.getLatitude(), src.getLatitude()) &&
                    between(southWest.getLatitude(), northWest.getLatitude(), dst.getLatitude()))
                return true;

        }

        // case where line is on right side
        if (src.getLongitude() == dst.getLongitude() && src.getLongitude() == northEast.getLongitude()){

            if (between(src.getLatitude(), dst.getLatitude(), northEast.getLatitude()) ||
                    between(src.getLatitude(), dst.getLatitude(), southEast.getLatitude()))
                return true;
            if (between(dst.getLatitude(), src.getLatitude(), northEast.getLatitude()) ||
                    between(dst.getLatitude(), src.getLatitude(), southEast.getLatitude()))
                return true;


            if (between(southEast.getLatitude(), northEast.getLatitude(), src.getLatitude()) &&
                    between(southEast.getLatitude(), northEast.getLatitude(), dst.getLatitude()))
                return true;
        }

        //case where line on with rectangle top
        if (src.getLatitude() == dst.getLatitude() && src.getLatitude() == northWest.getLatitude()){

            if (between(src.getLongitude(), dst.getLongitude(), northWest.getLongitude()) ||
                    between(src.getLongitude(), dst.getLongitude(), northEast.getLongitude()))
                return true;
            if (between(dst.getLongitude(), src.getLongitude(), northWest.getLongitude()) ||
                    between(dst.getLongitude(), src.getLongitude(), northEast.getLongitude()))
                return true;


            if (between(northWest.getLongitude(), northEast.getLongitude(), src.getLongitude()) &&
                    between(northWest.getLongitude(), northEast.getLongitude(), dst.getLongitude()))
                return true;
        }

        //case where line is on rectangle bottom
        if (src.getLatitude() == dst.getLatitude() && src.getLatitude() == southWest.getLatitude()){

            if (between(src.getLongitude(), dst.getLongitude(), southWest.getLongitude()) ||
                    between(src.getLongitude(), dst.getLongitude(), southEast.getLongitude()))
                return true;
            if (between(dst.getLongitude(), src.getLongitude(), southWest.getLongitude()) ||
                    between(dst.getLongitude(), src.getLongitude(), southEast.getLongitude()))
                return true;


            if (between(southWest.getLongitude(), southEast.getLongitude(), src.getLongitude()) &&
                    between(southWest.getLongitude(), southEast.getLongitude(), dst.getLongitude()))
                return true;
        }



        // checks if src and dst is both within rectangle
        if (between(northWest.getLongitude(), southEast.getLongitude(), src.getLongitude()) &&
                between(southEast.getLatitude(), northWest.getLatitude(), src.getLatitude()) &&
                between(northWest.getLongitude(), southEast.getLongitude(), dst.getLongitude()) &&
                between(southEast.getLatitude(), northWest.getLatitude(), dst.getLatitude()))
            return true;


        // slope = (y2 - y1) / (x2 - x1)
        Double lineSlope = (src.getLatitude() - dst.getLatitude()) / (src.getLongitude() - dst.getLongitude());
        Double bOfLine = (dst.getLatitude() - (lineSlope * dst.getLongitude()));

        Double topSlope = 0.0;
        Double bOfTopSlope = northWest.getLatitude();

        Double bottomSlope = 0.0;
        Double bOfBottomSlope = southEast.getLatitude();

        // x =  - b2-b1 / m1-m2


        // checks if intersection point is within bounds of top line of rectangle
        if (between(northWest.getLongitude(), southEast.getLongitude(), -(bOfTopSlope - bOfLine) / (topSlope - lineSlope)))
            return true;

        // bottom line
        if (between(northWest.getLongitude(), southEast.getLongitude(), -(bOfBottomSlope - bOfLine) / (bottomSlope - lineSlope)))
            return true;

        // left line y = mx + b (x is the left line, rest of variable uses line values)
        if (between(southWest.getLatitude(), northWest.getLatitude(), (lineSlope*northWest.getLongitude()) + bOfLine))
            return true;

        // right line
        if (between(southEast.getLatitude(), northEast.getLatitude(), (lineSlope*southEast.getLongitude() + bOfLine)))
            return true;



        // if line is vertical
        if (src.getLongitude() == dst.getLongitude() &&
                between(northWest.getLongitude(), northEast.getLongitude(), src.getLongitude())){

            if (between(southEast.getLatitude(), northEast.getLatitude(), src.getLatitude()) ||
                    between(southEast.getLatitude(), northEast.getLatitude(), dst.getLatitude()))
                return true;

            if ((src.getLatitude() >= northEast.getLatitude() && dst.getLatitude() <= southEast.getLatitude()) ||
                    (dst.getLatitude() >= northEast.getLatitude() && src.getLatitude() <= southEast.getLatitude()))
                return true;

        }



        return false;
    }

    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     *
     * @param lwb the lower boundary
     * @param upb the upper boundary
     * @param x   the value in question
     * @return true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }
}