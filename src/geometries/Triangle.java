package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * this class represents a Triangle
 *
 * @author Elhanan Tweig & Yossef Cohen-Salmon
 */
public class Triangle extends Polygon {
    /**
     * constructor for triangle by 3 points
     *
     * @param p1 coordinate value for X axis
     * @param p2 coordinate value for Y axis
     * @param p3 coordinate value for Z axis
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
