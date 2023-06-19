package geometries;


/**
 * base class who create the radial for every class who need it like sphere, cylinder...
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * the famous field radius of the class for the geometries with radius
     */
    protected final double radius;

    /**
     * the square of radius
     */
    protected final double radiusSquared;

    /**
     * constructor who take a double as a radius of our geometric object
     *
     * @param _radius length of the radius
     */
    RadialGeometry(double _radius) {
        radius = _radius;
        radiusSquared = _radius * _radius;
    }

    /**
     * getting radius
     *
     * @return radius of the radial geometry
     */
    @SuppressWarnings("unused")
    public double getRadius() {
        return radius;
    }
}
