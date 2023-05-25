package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private final Point location;
    private final Vector vTo;
    private final Vector vUp;
    private final Vector vRight;
    private double width;
    private double height;
    private double distance;
    private Vector direction;

    public Camera(Point _pt, Vector _vTo, Vector _vUp) {

        if (!(_vUp.dotProduct(_vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        location = _pt;
        vUp = _vUp.normalize();
        vTo = _vTo.normalize();
        vRight = _vTo.crossProduct(vUp).normalize();
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

    public Camera setVPSize(double _width, double _height) {
        width = _width;
        height = _height;
        return this;
    }

    public Camera setVPDistance(double _distance) {
        distance = _distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        Vector ijV = ijP.subtract(location);
        return new Ray(location, ijV);
    }
}
