package primitives;

public class Ray {
    private Point p0;
    private Vector dir;
    Ray(Point _p0, Vector _dir){
        p0 = _p0;
        dir = _dir;
    }
    Ray(Vector _dir){
        normalization(_dir);
        dir = _dir;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }
}
