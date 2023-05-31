package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     * Constructs a Scene with the specified name and initializes an empty collection of geometries.
     *
     * @param _name The name of the scene
     */
    public Scene(String _name) {
        name = _name;
        geometries = new Geometries();
        // Set the default background color to black
        background = Color.BLACK;
        // Set the default ambient light color to black
        ambientLight = new AmbientLight();
    }

    /**
     * Sets the background color of the scene.
     *
     * @param _color The background color to set
     * @return The scene object itself (this)
     */
    public Scene setBackground(Color _color) {
        background = _color;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param _ambientLight The ambient light to set
     * @return The scene object itself (this)
     */
    public Scene setAmbientLight(AmbientLight _ambientLight) {
        ambientLight = _ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param _geometries The geometries to set
     * @return The scene object itself (this)
     */
    public Scene setGeometries(Geometries _geometries) {
        geometries = _geometries;
        return this;
    }
}
