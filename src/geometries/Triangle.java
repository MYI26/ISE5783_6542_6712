package geometries;

import primitives.Point;

/**
 * this class represents a Triangle
 * <p>
 * Yona and Aaron
 */
public class Triangle extends Polygon {
    /**
     * constructor for triangle by 3 points
     *
     * @param _p1 coordinate value for X axis
     * @param _p2 coordinate value for Y axis
     * @param _p3 coordinate value for Z axis
     */
    public Triangle(Point _p1, Point _p2, Point _p3) {
        super(_p1, _p2, _p3);
    }
}
