package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This class represent a sphere like a ball and defined by point and radius
 *
 * @author Yona and Aaron
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor for sphere by point and radius
     *
     * @param _center point
     * @param _radius radius of sphere
     */
    public Sphere(Point _center, Double _radius) {
        super(_radius);
        this.center = _center;
    }

    /**
     * getting center
     *
     * @return center of sphere
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point _p) {
        return _p.subtract(center).normalize();
    }
}