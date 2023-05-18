package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
    @SuppressWarnings("unused")
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

    @Override
    public List<Point> findIntersections(Ray _ray) {
        Point p0 = _ray.getPoint();
        if (p0.equals(center))
            return List.of(_ray.getPoint(radius));

        Vector u = center.subtract(p0);
        double tm = _ray.getDir().dotProduct(u);
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = radiusSquared - dSquared;
        // if the line of the ray is outside or tangent to the sphere - there are no intersections
        if (alignZero(thSquared) <= 0) return null;

        double th = Math.sqrt(thSquared); // always t1 < t2
        double t2 = alignZero(tm + th);
        // if both points are behind the ray head
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0 ? List.of(_ray.getPoint(t2)) : List.of(_ray.getPoint(t1), _ray.getPoint(t2));
    }
}