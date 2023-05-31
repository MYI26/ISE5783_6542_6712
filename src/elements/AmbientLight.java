package elements;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight(Color iA, Double3 kA) {
        intensity=iA.scale(kA);
    }

    public AmbientLight() {
        // Set the intensity of the ambient light to black (Color.BLACK)
        this.intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}
