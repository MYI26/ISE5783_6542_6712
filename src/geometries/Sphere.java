package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a sphere like a ball and defined by point and radius
 *
 * @author Yona and Aaron
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor for sphere by point and radius
     *
     * @param _center point
     * @param _radius radius of sphere
     */
    public Sphere(Point _center, Double _radius) {
        super(_radius);
        this.center = _center;
    }

    /**
     * getting center
     *
     * @return center of sphere
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point _p) {
        return _p.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray _ray) {
        List<Point> intersectionPoints = new ArrayList<Point>();
        Vector sphereToRay = null;
        Point intersection = null;

        // Calcul du vecteur entre le centre de la sphère et le point de départ du rayon
        if(_ray.getPoint() != center)
            sphereToRay = _ray.getPoint().subtract(center);
        else{
            intersection = center.add(_ray.getDir().normalize());;
            intersectionPoints.add(intersection);}

        // Calcul du discriminant pour déterminer s'il y a une intersection
        double a = _ray.getDir().dotProduct(_ray.getDir());
        double b = 2 * _ray.getDir().dotProduct(sphereToRay);
        double c = sphereToRay.dotProduct(sphereToRay) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        // Si le discriminant est négatif, il n'y a pas d'intersection
        if (discriminant < 0) {
            return null;
        }

        // Calcul des points d'intersection
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (-b - sqrtDiscriminant) / (2 * a);

        if (t1 >= 0) {
            Point intersection1 = _ray.getPoint(t1);
            if(intersection1 != _ray.getPoint())
                intersectionPoints.add(intersection1);
        }

        if (t2 >= 0) {
            Point intersection2 = _ray.getPoint(t2);
            if(intersection2 != _ray.getPoint())
                intersectionPoints.add(intersection2);
        }

        if(intersectionPoints.isEmpty())
            return null;

        return intersectionPoints;
    }
}