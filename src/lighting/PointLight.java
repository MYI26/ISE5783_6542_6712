package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for a point light with position and without direction
 *
 * @author Mimoun^2 Yona and Aaron
 */
public class PointLight extends Light implements LightSource {

    private final Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor of point light
     *
     * @param _intensity=the color of the light
     * @param _position=the  position of the light
     */
    public PointLight(Color _intensity, Point _position) {
        super(_intensity);
        this.position = _position;
    }

    /**
     * setter for kc
     *
     * @param _kC the constant attenuation
     * @return the point light
     */
    public PointLight setKc(double _kC) {
        this.kC = _kC;
        return this;
    }

    /**
     * setter for kl
     *
     * @param _kL the linear attenuation
     * @return the point light
     */
    public PointLight setKl(double _kL) {
        this.kL = _kL;
        return this;
    }

    /**
     * setter for kq
     *
     * @param _kQ the quadratic attenuation
     * @return the point light
     */
    public PointLight setKq(double _kQ) {
        this.kQ = _kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point _p) {
        // IL / (kc + kl *distance + kq * distanceSquared)
        double distanceSquared = _p.distanceSquared(position);
        double factor = kC + kL * Math.sqrt(distanceSquared) + kQ * distanceSquared;
        return intensity.reduce(factor);
    }

    @Override
    public Vector getL(Point _p) {
        return _p.equals(position) ? null : _p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}