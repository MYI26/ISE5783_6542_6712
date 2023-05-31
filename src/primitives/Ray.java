package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Ray class represents the set of points on a line that are on one side of a given point on a line called the head of the fund.
 * Defined by point and direction
 *
 * @author Yona and Aaron
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    public Ray(Point _p0, Vector _dir) {
        p0 = _p0;
        dir = _dir.normalize();
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
     * @param points The collection of points
     * @return The closest point to the ray's head
     */
    /**
     * @param lst The list of all the points.
     * @return The closest point to p0 in the list.
     */
    public Point findClosestPoint(List<Point> lst) {
        if (lst == null || lst.size() == 0) return null;

        Point closest = lst.get(0);
        double closestDistance = p0.distanceSquared(closest); // To make the calculations more efficient.
        double tmpDist;
        for (Point point : lst) {
            tmpDist = p0.distanceSquared(point); // To make the calculations more efficient.
            if (tmpDist < closestDistance) {
                closest = point;
                closestDistance = tmpDist;
            }
        }
        return closest;
    }
}
