package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for a direction light without position
 *
 * @author Mimoun^ Yona and Aaron
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * constructor of direction light
     *
     * @param _intensity=the color of the light
     * @param _direction=the direction of the light
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this.direction = _direction.normalize();

    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}