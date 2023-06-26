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
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is on the other way of the ray
        assertEquals(new Point(0, 2, 3), ray.getPoint(-1), "ERROR: getPoint() does not return the correct point");
        // TC02: The point is ahead of the ray
        assertEquals(new Point(2, 2, 3), ray.getPoint(1), "ERROR: getPoint() does not return the correct point");

        // ============ boundary values tests ==================
        // TC03: The point is on the ray
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "ERROR: getPoint() does not return the correct point");
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