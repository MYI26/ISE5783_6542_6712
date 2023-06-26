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

    /**
     * getting normal
     *
     * @return normal of the sphere
     */
    @Override
    public Vector getNormal(Point _p) {
        return _p.subtract(center).normalize();
    }


    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray _ray, double _maxDistance) {
        Point p0 = _ray.getPoint();
        Vector v = _ray.getDir();

        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return alignZero(radius - _maxDistance) > 0 ? null : List.of(new GeoPoint(this, _ray.getPoint(radius)));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSqr = alignZero(u.lengthSquared() - tm * tm);
        double thSqr = radius * radius - dSqr;
        // no intersections : the ray direction is above the sphere
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(Math.sqrt(thSqr));

        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        if (alignZero(t1 - _maxDistance) > 0) return null;

        if (alignZero(t2 - _maxDistance) <= 0)
            return t1 <= 0 ? List.of(new GeoPoint(this, _ray.getPoint(t2)))
                    : List.of(new GeoPoint(this, _ray.getPoint(t1)), new GeoPoint(this, _ray.getPoint(t2)));
        else
            return t1 <= 0 ? null : List.of(new GeoPoint(this, _ray.getPoint(t1)));
    }

}