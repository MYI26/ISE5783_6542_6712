package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this class represent cylinder defined by like tube (ray and radius) and also with height
 *
 * @author Elhanan Tweig & Yossef Cohen-Salmon
 */
public class Cylinder extends Tube {

    final private double height;

    /**
     * constructor for Cylinder by ray radius and height
     *
     * @param axisRay the ray
     * @param radius  the radius
     * @param height  the height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getting height
     *
     * @return height of cylinder
     */
    public double getHeight() {
        return this.height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
