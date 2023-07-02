package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * this class represent cylinder defined by like tube (ray and radius) and also with height
 *
 * @author Yona and Aaron
 */
public class Cylinder extends Tube {

    final private double height;

    /**
     * constructor for Cylinder by ray radius and height
     *
     * @param _axisRay the ray (double)
     * @param _radius  the radius (double)
     * @param _height  the height (double)
     */
    public Cylinder(Ray _axisRay, double _radius, double _height) {
        super(_axisRay, _radius);
        this.height = _height;
    }

    /**
     * getting height
     *
     * @return height of cylinder
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * getting vector normal to the cylinder at point _point
     *
     * @param _point (Point)
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point _point) throws UnsupportedOperationException {
        Point p0 = axisRay.getPoint();
        Vector v = axisRay.getDir();

        // Check if the given point is the same as the base point of the axis
        if (_point.equals(p0))
            return v;

        // Calculate the projection of (point - p0) onto the axis ray
        Vector u = _point.subtract(p0);

        // Calculate the distance from p0 to the object in front of the given point
        double t = Util.alignZero(u.dotProduct(v));
        // If the given point is at the base of the object or at the top of the object
        if (t == 0 || Util.isZero(height - t))
            return v;

        return super.getNormal(_point);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
    /*@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }*/


}
