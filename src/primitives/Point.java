package primitives;

import static primitives.Util.isZero;

/**
 * this class serve a point with 3 coordinates, contains an object of three numbers of type Double3
 *
 * @author Yona and Aaron
 */
public class Point {

    Double3 xyz;

    public Point(Double x, Double y, Double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * constructor that gets Double3 and build point
     *
     * @param _xyz the xyz value
     */
    Point(Double3 _xyz) {
        xyz = _xyz;
    }

    /**
     * the function calculates vector plus point
     *
     * @param _v the vector that we want to add
     * @return new point of the result
     */
   public Point add(Vector _v) {
       return new Point(xyz.add(_v.xyz));
   }

    /**
     * the function calculates point minus point
     *
     * @param _p the point that we want to subtract
     * @return new point of the result
     */
    public Vector subtract(Point _p){
        if(_p.equals(this)){
            throw new IllegalArgumentException("unable to create a vector with (0,0,0) as coordinate");
        }
        return new Vector(new Double3(xyz.d1-_p.xyz.d1,xyz.d2-_p.xyz.d2,xyz.d3-_p.xyz.d3));
    }

    /**
     * the function calculates the distance by minus between them and pow 2 every value
     *
     * @param other the second point
     * @return the result of the distance in pow 2
     */
        public Double distanceSquared(Point other) {
            final Double x1 = xyz.d1;
            final Double y1 = xyz.d2;
            final Double z1 = xyz.d3;
            final Double x2 = other.xyz.d1;
            final Double y2 = other.xyz.d2;
            final Double z2 = other.xyz.d3;

            return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
        }

    /**
     * the function calculates the distance between 2 points by making root
     * to the value that return from distance squared
     *
     * @param p the second point
     * @return the distance squared between 2 points
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point other))
            return false;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "(" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}
