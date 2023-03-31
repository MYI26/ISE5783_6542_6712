package geometries;

public abstract class RadialGeometry implements Geometry{
    protected d0ouble radius;

    RadialGeometry(Double _radius){
        radius = _radius;
    }

    public RadialGeometry (RadialGeometry other)
    {
        radius = other.radius;
    }

    public double getRadius()
    {
        return radius;
    }
}
