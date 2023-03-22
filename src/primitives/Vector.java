package primitives;

/**
 * This class does operations on vectors
 *
 * @author Yona and Aaron
 */
public class Vector extends Point{

    Vector(Double3 _xyz){ super(_xyz); }

    public Vector(Double x, Double y, Double z){
        super(x ,y ,z);
        if (Double3.ZERO.equals(xyz)) throw new IllegalArgumentException("ERROR: zero vector");}

    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    public Vector crossProduct(Vector v) {
        double d1 = xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2;
        double d2 = xyz.d1 * v.xyz.d3 - xyz.d3 * v.xyz.d1;
        double d3 = xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1;
        return new Vector(d1, -d2, d3);
    }

    public double lengthSquared() {
        return dotProduct(this);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
