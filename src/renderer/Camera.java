package renderer;

import primitives.Point;
import primitives.Vector;

public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;

    public Camera(Point _pt, Vector _v1, Vector _v2) {
        double dotProduct = _v1.dotProduct(_v2);
        if (dotProduct != 0) throw new IllegalArgumentException("The two vector are not perpendicular");
        location = _pt;
        vUp = _v1.normalize();
        vTo = _v2.normalize();
        vRight = (_v1.crossProduct(_v2)).normalize();
    }

    public Point getLocation() {
        return location;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }
}
