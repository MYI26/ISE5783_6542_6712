package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class representing a base class for ray tracing algorithms.
 */
public abstract class RayTraceBase {

    /**
     * we create a scene
     */
    protected final Scene scene;

    /**
     * Constructs a RayTraceBase object with the specified scene.
     *
     * @param scene The scene to be used for ray tracing.
     */
    public RayTraceBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method to trace a ray and calculate the resulting color.
     *
     * @param _ray The ray to be traced.
     * @return The color calculated from tracing the ray.
     */
    public abstract Color traceRay(Ray _ray);
}
