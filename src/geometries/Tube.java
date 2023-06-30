package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.discriminantParam;

/**
 * this class represents a tube defined by ray and radius
 *
 * @author Yona and Aaron
 */
public class Tube extends RadialGeometry {

    /**
     * the ray of the principle axe's tube
     */
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

    /**
     * getting normal
     *
     * @return normal of the tube
     */
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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Extract the origin and direction of the ray
        Point rayOrigin = ray.getPoint();
        Vector rayDirection = ray.getDir();

        // Calculate the discriminant of the quadratic equation
        double[] abc = discriminantParam(rayDirection, rayOrigin, ray, radius);

        double discriminant = abc[1] * abc[1] - 4 * abc[0] * abc[2];

        // If the discriminant is negative or all intersection points are beyond the
        // maximum distance,
        // the ray does not intersect the cylinder
        if (discriminant < 0) {
            return null;
        }

        // Calculate the roots of the quadratic equation
        double t1 = (-abc[1] - Math.sqrt(discriminant)) / (2 * abc[0]);
        double t2 = (-abc[1] + Math.sqrt(discriminant)) / (2 * abc[0]);

        // Check if both intersection points are beyond the maximum distance
        if (t1 > maxDistance && t2 > maxDistance) {
            return null;
        }

        // Calculate the intersection points
        Point intersectionPoint1 = ray.getPoint().add(ray.getDir().scale(t1));
        Point intersectionPoint2 = ray.getPoint().add(ray.getDir().scale(t2));

        // Add the intersection points to the list
        return List.of(new GeoPoint(this, intersectionPoint1), new GeoPoint(this, intersectionPoint2));
    }

}


