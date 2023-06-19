package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface that is the base interface of all our geometric class like triangle,Sphere...
 */
public abstract class Geometry extends Intersectable {
    /**
     * the color of the geometry
     */
    private Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * setter for material
     *
     * @param _material the material of the geometry object
     * @return the geometry object
     */
    public Geometry setMaterial(Material _material) {
        this.material = _material;
        return this;
    }

    /**
     * getter for material
     *
     * @return the material of the geometry object
     */
    public Material getMaterial() {
        return material;
    }

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
     * @param _p the Point
     * @return the normal vector
     */
    public abstract Vector getNormal(Point _p);
}
