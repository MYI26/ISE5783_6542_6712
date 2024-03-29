package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Ray class represents the set of points on a line that are on one side of a given point on a line called the head of the fund.
 * Defined by point and direction
 *
 * @author Yona and Aaron
 */
public class Ray {
    private static final double DELTA = 0.1;
    private final Point p0;
    private final Vector dir;

    /**
     * constructor for a ray by point and vector
     *
     * @param _p0  point
     * @param _dir vector
     */
    public Ray(Point _p0, Vector _dir) {
        p0 = _p0;
        dir = _dir.normalize();
    }

    /**
     * Constructor for ray with offset to avoid errors during the lancer of ray
     *
     * @param _point     original point laying on the surface of the geometry
     * @param _direction vector direction - <b>must be normalized</b>
     * @param _n         normal vector from the geometry - <b>must be normalized</b>
     */
    public Ray(Point _point, Vector _direction, Vector _n) {
        // Compute the offset vector based on the orientation of the normal
        double nl = _direction.dotProduct(_n);
        Vector offset = _n.scale(nl > 0 ? DELTA : -DELTA);
        this.p0 = _point.add(offset);
        this.dir = _direction;
    }

    /**
     * The function returns the origin point on the ray
     *
     * @return the point
     */
    public Point getPoint() {
        return this.p0;
    }

    /**
     * The function returns the calculation of the destination point on the ray after multiplication of the direction by a scalar
     *
     * @param _t distance from the ray head to the point with ray direction
     * @return the point
     */
    public Point getPoint(double _t) {
        return isZero(_t) ? p0 : p0.add(dir.scale(_t));
    }

    /**
     * get the vector on the ray
     *
     * @return vector on the ray
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) return true;
        return _obj instanceof Ray other && this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "coordinate Point: " + p0.toString() + "coordinate Vector: " + dir.toString();
    }

    /**
     * Finds the closest point to the ray's head from a collection of points.
     *
     * @param _points The list of all the points.
     * @return The closest point to p0 in the list.
     */
    public Point findClosestPoint(List<Point> _points) {
        return _points == null || _points.isEmpty() ? null
                : findClosestGeoPoint(_points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * find the closest GeoPoint to the beginning of the ray
     *
     * @param _geoPoints the geo points
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> _geoPoints) {
        if (_geoPoints == null)
            return null;

        GeoPoint result = null;
        Double closest = Double.POSITIVE_INFINITY;
        for (GeoPoint p : _geoPoints) {
            double temp = p.point.distance(p0);
            if (temp < closest) {
                closest = temp;
                result = p;
            }
        }
        return result;
    }
}
