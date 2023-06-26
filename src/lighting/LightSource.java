package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface for all the lights
 *
 * @author Yona and Aaron Mimoun
 */
public interface LightSource {

    /**
     * get the light intensity at a point
     *
     * @param _p the point
     * @return the light intensity at the point as color
     */
    public Color getIntensity(Point _p);

    /**
     * get the direction of the light towards the point
     *
     * @param _p the point
     * @return the direction from the light to the point
     */
    public Vector getL(Point _p);

    /**
     * calculate the distance between the point to the light source
     *
     * @param _point the point
     * @return the distance from point to the light
     */
    double getDistance(Point _point);
}