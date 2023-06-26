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
     * @param intensity=the color of the light
     * @param position=the  position of the light
     * @param direction=the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }


    @Override
    public Color getIntensity(Point p) {
        double dirL = direction.dotProduct(getL(p));
        if (alignZero(dirL) <= 0) return Color.BLACK;
        //check if it is flashlight
        if (narrowBeam != 1) dirL = Math.pow(dirL, narrowBeam);
        return super.getIntensity(p).scale(dirL);
    }

    /**
     * setter for narrowBeam
     *
     * @param narrowBeam the new value for narrowBeam
     * @return this light
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}