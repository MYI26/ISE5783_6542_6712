package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

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

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            //ray did not intersect any geometrical object
            return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * A function to check the color of a point.
     *
     * @param point The given point.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint point, Ray _ray) {
        return scene.ambientLight.getIntensity();
    }

}
