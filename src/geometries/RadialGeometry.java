package geometries;


/**
 * base class who create the radial for every class who need it like sphere, cylinder...
 */
public abstract class RadialGeometry extends Geometry {
    protected final double radius;
    protected final double radiusSquared;

    /**
     * constructor who take a double as a radius of our geometric object
     *
     * @param _radius
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
