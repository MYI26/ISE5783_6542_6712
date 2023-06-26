package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class for a spotLight - light with direction and position
 *
 * @author Yona and Aaron Mimoun
 */
public class SpotLight extends PointLight {

    private Vector direction;
    private double narrowBeam = 1;

    /**
     * constructor of spotLight
     *
     * @param _intensity=the color of the light
     * @param _position=the  position of the light
     * @param _direction=the direction of the light
     */
    public SpotLight(Color _intensity, Point _position, Vector _direction) {
        super(_intensity, _position);
        this.direction = _direction.normalize();
    }


    @Override
    public Color getIntensity(Point _p) {
        double dirL = direction.dotProduct(getL(_p));
        if (alignZero(dirL) <= 0) return Color.BLACK;
        //check if it is flashlight
        if (narrowBeam != 1) dirL = Math.pow(dirL, narrowBeam);
        return super.getIntensity(_p).scale(dirL);
    }

    /**
     * setter for narrowBeam
     *
     * @param _narrowBeam the new value for narrowBeam
     * @return this light
     */
    public SpotLight setNarrowBeam(double _narrowBeam) {
        this.narrowBeam = _narrowBeam;
        return this;
    }
}