package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light source represents a non-directional, fixed-intensity and fixed-color light source.
 *
 * @author Yona and Aaron Mimoun
 */
public class AmbientLight {

    private final Color intensity;

    /**
     * the absence of any ambient light
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs a new instance of ambient light with intensity according to the parameters
     * The final intensity is iA * kA.
     *
     * @param iA Intensity of the ambient light.
     * @param kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color iA, Double3 kA) {
        intensity = iA.scale(kA);
    }

    /**
     * Constructs a new instance of ambient light with intensity according to the parameters
     * The final intensity is iA * kA.
     *
     * @param iA Intensity of the ambient light.
     * @param kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color iA, double kA) {
        intensity=iA.scale(kA);
    }

    /**
     * get the original intensity of the light - I<sub>0</sub>
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
