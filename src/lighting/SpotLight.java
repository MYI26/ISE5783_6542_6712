package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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
        //check if it is flashlight
        if (narrowBeam != 1) {
            return super.getIntensity(p).scale(Math.pow(Math.max(0, direction.dotProduct(getL(p))), narrowBeam));
        }
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
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