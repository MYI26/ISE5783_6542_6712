package primitives;

/**
 * This class does operations on vectors
 *
 * @author Yona and Aaron
 */
public class Vector extends Point {

    /**
     * constructor for the vector
     *
     * @param _xyz value of 3 coordinate
     */
    Vector(Double3 _xyz) {
        super(_xyz);
        if (Double3.ZERO.equals(xyz)) throw new IllegalArgumentException("ERROR: zero vector");
    }


    public Vector(double _x, double _y, double _z) {
        super(_x, _y, _z);
        if (Double3.ZERO.equals(xyz)) throw new IllegalArgumentException("ERROR: zero vector");
    }

    /**
     * add method
     *
     * @param _other the vector to add
     * @return algebraic added vector
     */
    public Vector add(Vector _other) {
        return new Vector(xyz.add(_other.xyz));
    }

    /**
     * Scale (multiply) floating point triad by a number into a new triad where each
     * number is multiplied by the number
     *
     * @param _scalar right handle side operand for scaling
     * @return result of scale in vector
     */
    public Vector scale(double _scalar) {
        return new Vector(xyz.scale(_scalar));
    }

    /**
     * dot product between two vectors (scalar product)
     *
     * @param _v the right vector of U.V
     * @return scalre value of dot product
     */
    public double dotProduct(Vector _v) {
        return xyz.d1 * _v.xyz.d1 + xyz.d2 * _v.xyz.d2 + xyz.d3 * _v.xyz.d3;
    }

    /**
     * Cross product (vectorial product)
     *
     * @param _v second vector
     * @return new vector resulting from cross product
     */
    public Vector crossProduct(Vector _v) {
        double d1 = xyz.d2 * _v.xyz.d3 - xyz.d3 * _v.xyz.d2;
        double d2 = xyz.d1 * _v.xyz.d3 - xyz.d3 * _v.xyz.d1;
        double d3 = xyz.d1 * _v.xyz.d2 - xyz.d2 * _v.xyz.d1;
        return new Vector(d1, -d2, d3);
    }

    /**
     * calculating the length of vector
     *
     * @return euclidean length squared of the vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Calculating the length of vector by Pythagoras
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * A normalization operation
     *
     * @return a new vector normalized in the same direction as the original vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) return true;
        return _obj instanceof Vector other && super.equals(other);

        //if (_obj == null)
          //  return false;
        //if (!(_obj instanceof Vector other))
          //  return false;
       // return super.equals(other);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
