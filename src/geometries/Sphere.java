package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represent a sphere like a ball and defined by point and radius
 *
 * @author Yona and Aaron
 */
public class Sphere extends RadialGeometry {
    final private Point center;

    /**
     * constructor for sphere by point and radius
     *
     * @param center point
     * @param radius radius of sphere
     */
    public Sphere(Point center, Double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * getting center
     *
     * @return center of sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getting radius
     *
     * @return radius of sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return p.substract(center).normalize();
    }
}