package geometries;


/**
 * base class who create the radial for every class who need it like sphere, cylinder...
 */
public abstract class RadialGeometry implements Geometry {
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

    @SuppressWarnings("unused")
    public double getRadius() {
        return radius;
    }
}
