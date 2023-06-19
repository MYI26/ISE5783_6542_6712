package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene with a background, lights and geometries.
 *
 * @author Yona and Aaron Mimoun
 */
public class Scene {

    /**
     * scene name
     */
    public final String name;

    /**
     * background color - default is black
     */
    public Color background = Color.BLACK;

    /**
     * Ambient light - default is no ambient light
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * geometries- default is no geometries
     */
    public Geometries geometries = new Geometries();

    /**
     * A list of all kind of light
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a Scene with the specified name and initializes an empty collection of geometries.
     *
     * @param _name The name of the scene
     */
    public Scene(String _name) {
        name = _name;
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

    /**
     * set the scene`s light
     *
     * @param lights new light
     * @return the updated scene itself
     */
    @SuppressWarnings("unused")
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}
