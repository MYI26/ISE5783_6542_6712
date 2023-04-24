package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * this class represents a tube defined by ray and radius
 *
 * @author Yona and Aaron
 */
public class Tube extends RadialGeometry {
    final protected Ray axisRay;

    /**
     * constructor for Tube by ray and radius
     *
     * @param axisRay the ray
     * @param radius  the radius
     */
    public Tube(Ray axisRay, Double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * get the ray
     *
     * @return the ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * get the radius
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getPoint()));
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
