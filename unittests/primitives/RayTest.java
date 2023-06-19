package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * A class to test the Ray class.
 */
class RayTest {

    /**
     * we create the point (0,0,0) the center of the axes
     */
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Tests the getPointTest function in Ray class.
     */
    @Test
    void getPointTest() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: the vector of the ray is positive
        Vector v = new Vector(0, 0, 1);
        Ray r = new Ray(ZERO_POINT, v);
        assertEquals(new Point(0., 0., 2.), r.getPoint(2), "TC01: the function getPoint(t) failed");

        // TC02: the vector of the ray is negative
        assertEquals(new Point(0., 0., -2.), r.getPoint(-2), "TC01: the function getPoint(t) failed");

        // =============== Boundary Values Tests ==================
        // TC11: parameter is 0
        assertEquals(ZERO_POINT, r.getPoint(0), "TC01: the function getPoint(t) failed");

    }

    /**
     * Tests the findClosestPoint function in Ray class.
     */
    @Test
    void findClosestPointTest() {

        Ray ray = new Ray(ZERO_POINT, new Vector(1, 0, 0));
        Point a = new Point(8, 0, 0);
        Point b = new Point(2, 0, 0);
        Point c = new Point(5, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point is the closest
        List<Point> lst = List.of(a, b, c);
        assertEquals(b, ray.findClosestPoint(lst), "TC01: The middle point is the closest test failed");

        // =============== Boundary Values Tests ==================
        // TC11: Empty set
        lst = List.of();
        assertNull(ray.findClosestPoint(lst), "TC11: Empty set test failed");

        // TC12: First point is closest
        lst = List.of(b, c, a);
        assertEquals(b, ray.findClosestPoint(lst), "TC12: First point is the closest test failed");

        // TC13: Last point is the closest
        lst = List.of(c, a, b);
        assertEquals(b, ray.findClosestPoint(lst), "TC13: Last point is the closest test failed");
    }

}