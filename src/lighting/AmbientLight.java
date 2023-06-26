package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light source represents a non-directional, fixed-intensity and fixed-color light source.
 *
 * @author Yona and Aaron Mimoun
 */
public class AmbientLight extends Light {

    /**
     * the absence of any ambient light
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs a new instance of ambient light with intensity according to the parameters.<br>
     * The final intensity is iA * kA.
     *
     * @param _iA Intensity of the ambient light.
     * @param _kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color _iA, Double3 _kA) {
        super(_iA.scale(_kA));
    }

    /**
     * Constructs a new instance of ambient light with intensity according to the parameters.<br>
     * The final intensity is iA * kA.
     *
     * @param _iA Intensity of the ambient light.
     * @param _kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color _iA, double _kA) {
        super(_iA.scale(_kA));
    }
}
