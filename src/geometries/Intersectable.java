package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * interface that will be used for all the intersections points.
 */
public interface Intersectable {

    /**
     * return a list of all the intersection point between the ray and the geometry
     * @param _ray
     * @return
     */
    List<Point> findIntersections(Ray _ray);
}
