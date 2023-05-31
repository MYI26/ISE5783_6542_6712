package renderer;

import primitives.Point;
import scene.Scene;
import primitives.Color;
import primitives.Ray;

/**
 * Basic ray tracer implementation.
 */
public class RayTracerBasic extends RayTraceBase {

    /**
     * Constructs a RayTracerBasic object with the specified scene.
     *
     * @param scene The scene to be used for ray tracing.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and calculates the resulting color using basic ray tracing algorithm.
     *
     * @param ray The ray to be traced.
     * @return The color calculated from tracing the ray.
     */
    @Override
    public Color traceRay(Ray ray) {
        Point closestPoint = ray.findClosestPoint(scene.geometries.findIntersections(ray));
        return closestPoint == null ? scene.background : calcColor(closestPoint);

    }
    /**
     * A function to check the color of a point.
     * @param point The given point.
     * @return The color of the point.
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

}
