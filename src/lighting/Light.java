package lighting;

import primitives.Color;

/**
 * represent the Lights by light intensity as Color
 *
 * @author Mimoun^2 Yona and Aaron
 */
public abstract class Light {

    /**
     * the intensity of the light represents his color
     */
    protected final Color intensity;

    /**
     * constructor for light
     *
     * @param _intensity the intensity color
     */
    protected Light(Color _intensity) {
        this.intensity = _intensity;
    }

    /**
     * getter for intensity
     *
     * @return the intensity color
     */
    public Color getIntensity() {
        return intensity;
    }
}
