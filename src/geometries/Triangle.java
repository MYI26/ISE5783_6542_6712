package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents a Triangle.
 * Yona and Aaron
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
    public List<Point> findIntersections(Ray _ray) {
        List<Point> planeIntersections = plane.findIntersections(_ray);
        if (planeIntersections == null)
            return null; // No intersection with the plane

        Point p = planeIntersections.get(0); // Intersection point with the plane

        // Calculate vectors for edges of the triangle
        Vector v0 = vertices.get(2).subtract(vertices.get(0));
        Vector v1 = vertices.get(1).subtract(vertices.get(0));
        Vector v2 = p.subtract(vertices.get(0));

        // Calculate dot products
        double dot00 = v0.dotProduct(v0);
        double dot01 = v0.dotProduct(v1);
        double dot02 = v0.dotProduct(v2);
        double dot11 = v1.dotProduct(v1);
        double dot12 = v1.dotProduct(v2);

        // Compute barycentric coordinates
        double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        if (alignZero(u) <= 0) return null;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
        if (alignZero(v) <= 0) return null;

        // Check if the point is inside the triangle
        if (alignZero(u + v - 1) >= 0) return null;

        return planeIntersections;
    }
}
