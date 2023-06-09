package geometries;

import primitives.Color;
import primitives.Point;

/**
 * Geometry interface that is the base interface of all our geometric class like triangle,Sphere...
 */
public abstract class Geometry extends Intersectable {
    /**
     * the color of the geometry
     */
    protected Color emission = Color.BLACK;

    /**
     * getter for emission
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for emission
     *
     * @param _emission the emission color
     * @return the geometry object
     */
    public Geometry setEmission(Color _emission) {
        emission = _emission;
        return this;
    }

    /**
     * getNormal is the function who return the normal vector from the point sent as parameter
     *
     * @param _p
     * @return the normal vector
     */
    public abstract getNormal(Point _p);
}
