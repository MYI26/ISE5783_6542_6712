package primitives;

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
}
