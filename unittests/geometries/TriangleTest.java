package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link Triangle} class
 *
 * @author Yona & Aaron Mimoun
 */
class TriangleTest {

    /**
     * testing if the normal vector is obtained correctly when it is positive
     *
     * Test method for {@link Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormalPos() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0., 0., 1.), new Point(1., 0., 0.), new Point(0., 0., 0.));
        assertEquals(new Vector(0, 1, 0), tr.getNormal(new Point(0.3, 0., 0.3)), "TC01: Bad normal to triangle");
    }

    /**
     * testing if the normal vector is obtained correctly when it is negative
     *
     * Test method for {@link Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormalNeg() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(1., 0., 0.), new Point(0., 0., 1.), new Point(0., 0., 0.));
        assertEquals(new Vector(0, -1, 0), tr.getNormal(new Point(0.3, 0., 0.3)), "TC01: Bad normal to triangle");
    }

}