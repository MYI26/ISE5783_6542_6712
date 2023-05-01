package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * this class represents a tube defined by ray and radius
 *
 * @author Yona and Aaron
 */
public class Tube extends RadialGeometry {
    protected final Ray axisRay;


    /**
     * constructor for Tube by ray and radius
     *
     * @param _axisRay the ray
     * @param _radius  the radius
     */
    public Tube(Ray _axisRay, double _radius) {
        super(_radius);
        this.axisRay = _axisRay;
    }

    /**
     * get the ray
     *
     * @return the ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point _p) {
        double t = axisRay.getDir().dotProduct(_p.subtract(axisRay.getPoint()));
        return _p.subtract(axisRay.getPoint(t)).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
