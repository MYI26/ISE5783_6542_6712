package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

/**
 * Unit tests for {@link Sphere} class
 *
 * @author Yona & Aaron Mimoun
 */
class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests =============
        //        //TC01: Test that the normal is proper
        Sphere sph = new Sphere(new Point(0., 0., 1.), 1.0);
        assertEquals(new Vector(0, 0, 1), sph.getNormal(new Point(0., 0., 2.)), "TC01: Wrong normal to sphere");

        Sphere sp = new Sphere(new Point(0., 0., 0.), 1.);
        assertEquals(sp.getNormal(new Point(0., 0., 1.)), new Vector(0, 0, 1), "TC01: Sphere.getNormal() gives wrong normal.");
    }
}