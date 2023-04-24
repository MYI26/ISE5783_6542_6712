package primitives;

import static primitives.Util.isZero;

/**
 * Ray class represents the set of points on a line that are on one side of a given point on a line called the head of the fund.
 * Defined by point and direction
 *
 * @author Yona and Aaron
 */
public class Ray {
    private Point p0;
    private Vector dir;

    public Ray(Point _p0, Vector _dir){
        p0 = _p0;
        _dir.normalize();
        dir = _dir;
    }

    public Point getPoint()
    {
        return this.p0;
    }

    /**
     * The function returns the calculation of the pont on the ray
     *
     * @param t distance from the ray head to the point with ray direction
     * @return the point
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : p0.add(dir.scale(t));
    }

    public Vector getVector()
    {
        return this.dir;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return "coordinate Point: "+p0.toString() +"coordinate Vector: "+dir.toString();
    }
}
