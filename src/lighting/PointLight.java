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

    private Point position;
    private double kC, kL, kQ;

    /**
     * constructor of point light
     *
     * @param _intensity=the color of the light
     * @param _position=the  position of the light
     */
    public PointLight(Color _intensity, Point _position) {
        super(_intensity);
        this.position = _position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
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
        double distance = _p.distance(position);
        double distanceSquared = _p.distanceSquared(position);

        double factor = kC + kL * distance + kQ * distanceSquared;

        return getIntensity().reduce(factor);
    }

    @Override
    public Vector getL(Point _p) {
        if (!_p.equals(position))
            return _p.subtract(position).normalize();
        return null;
    }
}