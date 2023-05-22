package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon implements Geometry {

    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        size = vertices.length;
        if (size < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[size - 1].subtract(vertices[size - 2]);
        Vector edge2 = vertices[0].subtract(vertices[size - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < size; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * getting normal
     *
     * @return normal of plane
     */
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * getting the intersection's points between the ray with the polygon
     *
     * @param _ray (Ray)
     * @return the intersection's points
     */
    public List<Point> findIntersections(Ray _ray) {
        List<Point> intersections = new ArrayList<>();

        // Calcul de l'intersection entre le rayon et le plan du polygone
        List<Point> planeIntersections = plane.findIntersections(_ray);
        if (planeIntersections.isEmpty())
            return intersections;

        Point intersectionPoint = planeIntersections.get(0);

        // Vérification si le point d'intersection se trouve à l'intérieur du polygone
        if (isPointInsidePolygon(intersectionPoint)) {
            intersections.add(intersectionPoint);
        }

        return intersections;
    }

    /**
     * getting true if the point is inside the polygon
     *
     * @param _point
     */
    private boolean isPointInsidePolygon(Point _point) {
        Vector v1 = vertices.get(1).subtract(vertices.get(0));
        Vector v2 = vertices.get(2).subtract(vertices.get(1));
        Vector normal = v1.crossProduct(v2).normalize();

        for (int i = 0; i < vertices.size(); i++) {
            Vector edge = vertices.get((i + 1) % vertices.size()).subtract(vertices.get(i));
            Vector toPoint = _point.subtract(vertices.get(i));

            if (normal.dotProduct(edge.crossProduct(toPoint)) < 0)
                return false;
        }

        return true;
    }
}
