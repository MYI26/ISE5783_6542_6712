package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Plane} class
 *
 * @author Yona &amp; Aaron Mimoun
 */
class PlaneTest {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        Point p0 = new Point(1., 2., 3.);
        Point p1 = new Point(1., 2., 3.);
        Point p2 = new Point(4., 5., 6.);

        //TC11: Test that constructor doesn't accept two points equals
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p1, p2), "TC11: the constructor must throw exception when two point equals");

        //TC12: Test that constructor doesn't accept three points that are co-lined
        Point p3 = new Point(2., 4., 6.);
        Point p4 = new Point(4., 8., 12.);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p3, p4), "TC12: the constructor must throw exception when three points are co-lined");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test that the normal is proper
        Plane pl = new Plane(new Point(0., 0., 1.), new Point(1., 0., 0.), new Point(0., 1., 0.));
        double sqrt3 = Math.sqrt(1d / 3);
        Vector norm = pl.getNormal(new Point(0., 0., 1.));
        boolean normal1 = new Vector(sqrt3, sqrt3, sqrt3).equals(norm);
        boolean normal2 = new Vector(-sqrt3, -sqrt3, -sqrt3).equals(norm);
        assertTrue(normal1 || normal2, "Wrong normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(-0.5, -0.5, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Point p1 = new Point(0, 1, 0);
        List<Point> result = plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, 0, -1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses plane");

        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 2))), "TC02: Ray does not intersect the plane EP doesn't work.");

        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel to the plane and included
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))), "TC11: Ray is parallel and included in the plane BVA doesn't work.");

        // TC12: Ray is parallel to the plane but not included
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(1, 0, 0))), "TC12: Ray is parallel and not included in the plane BVA doesn't work.");

        // TC13: Ray is orthogonal to the plane when the ray starts before
        Point p2 = new Point(0, 1, 0);
        result = plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p2), result, "TC13: Ray is orthogonal to the plane and before the plane BVA doesn't work.");

        // TC14: Ray is orthogonal to the plane but the ray starts in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 0, -1))), "TC14: Ray is orthogonal to the plane and in the plane BVA doesn't work.");

        // TC15: Ray is orthogonal to the plane but the starts after
        assertNull(plane.findIntersections(new Ray(new Point(0, 2, -1), new Vector(0, 0, -1))), "TC15: Ray is orthogonal to the plane and after the plane BVA doesn't work.");

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        assertNull(plane.findIntersections(new Ray(plane.getQ0(), new Vector(1, 1, 0))), "TC16: Ray begins in the same point which appears as the plane's reference point BVA doesn't work.");

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 1, -1))), "TC17: Ray begins at the plane but the ray it's not parallel or orthogonal to the plane BVA doesn't work.");

    }
}