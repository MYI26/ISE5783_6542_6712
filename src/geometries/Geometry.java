package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface that is the base interface of all our geometric class like triangle,Sphere...
 *
 */
public interface Geometry {
    /**
     * getNormal is the function who return the normal vector from the point sent as parameter
     * @param _p
     * @return the normal vector
     */
    public Vector getNormal(Point _p);
}
