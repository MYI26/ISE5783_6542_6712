package primitives;

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
    public Point getVector()
    {
        return this.dir;
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
