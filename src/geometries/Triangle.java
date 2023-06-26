package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents a Triangle.
 *
 * @author Yona and Aaron
 */
public class Triangle extends Polygon {

    /**
     * Constructor for Triangle by 3 points
     *
     * @param _p1 first point
     * @param _p2 second point
     * @param _p3 third point
     */
    public Triangle(Point _p1, Point _p2, Point _p3) {
        super(_p1, _p2, _p3);
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray _ray, double _maxDistance) {
        var result = plane.findGeoIntersectionsHelper(_ray, _maxDistance);

        //Check if the ray intersect the plane.
        if (result == null) return null;

        for (GeoPoint g : result)
            g.geometry = this;

        Point p0 = _ray.getPoint();
        Vector v = _ray.getDir();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double vn1 = alignZero(v.dotProduct(n1));
        if (vn1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double vn2 = alignZero(v.dotProduct(n2));
        if (vn1 * vn2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double vn3 = alignZero(v.dotProduct(n3));
        if (vn1 * vn3 <= 0) return null;

        return result;
    }
}
