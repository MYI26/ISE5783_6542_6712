package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * this class represents a Triangle
 * <p>
 * Yona and Aaron
 */
public class Triangle extends Polygon {
    /**
     * constructor for triangle by 3 points
     *
     * @param _p1 coordinate value for X axis
     * @param _p2 coordinate value for Y axis
     * @param _p3 coordinate value for Z axis
     */
    public Triangle(Point _p1, Point _p2, Point _p3) {
        super(_p1, _p2, _p3);
    }
    @Override
    public List<Point> findIntersections(Ray _ray) {

        List<Point> list =plane.findIntersections(_ray);

            //intersection point of the plane who contain the triangle and the ray
            Point p ;
            p = list.get(0);
            if (p==null) return null;

            //v0,v1,v2 are the sides of the triangle
            Vector v0 = vertices.get(2).subtract(vertices.get(0));
            Vector v1 = vertices.get(1).subtract(vertices.get(0));
            Vector v2 = p.subtract(vertices.get(0));

            double dot00 = v0.dotProduct(v0);
            double dot01 = v0.dotProduct(v1);
            double dot02 = v0.dotProduct(v2);
            double dot11 = v1.dotProduct(v1);
            double dot12 = v1.dotProduct(v2);
            //centroids calculation to finally check if the point is inside the triagle
            double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
            double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
            double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

            // checking if the point is inside the triangle
            if( (u >= 0) && (v >= 0) && (u + v < 1))return list;
            else return  null;
        }
    }
