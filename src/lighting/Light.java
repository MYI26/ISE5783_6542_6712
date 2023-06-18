package lighting;

import primitives.Color;

/**
 * represent the Lights by light intensity as Color
 *
 * @author Mimoun^2 Yona and Aaron
 */
public abstract class Light {
    protected final Color intensity; //light intensity as Color       //check it

    /**
     * constructor for light
     *
     * @param intensity the intensity color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
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
