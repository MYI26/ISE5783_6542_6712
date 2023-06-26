package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of a ray tracer class.
 */

public class RayTracerBasic extends RayTraceBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private double snellParameter = 1;

    /**
     * Constructs a new instance of ray tracer with a given scene.
     *
     * @param scene The given scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * calculate the color at a specific point
     *
     * @param geoPoint a geo point
     * @param ray      a ray
     * @return the color at this point
     */
    /* returns the color at a certain point
     *
     * @param intersection with the geometry
     * @param ray   the ray from the viewer
     * @return Color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = n.dotProduct(v);
        if (isZero(nv))
            return Color.BLACK;

        Color color = calcLocalEffects(geoPoint, v, n, nv);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, v, n, nv, level, k));
    }

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());

    }

    /**
     * calculated light contribution from all light sources
     *
     * @param gp the geo point we calculate the color of
     * @param v  the direction of the ray that caused the intersection
     * @param n  the normal to the geometry surface at the intersection point
     * @param nv dot-product of (n,v)
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint gp, Vector v, Vector n, double nv) {
        Color color = gp.geometry.getEmission();
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = n.dotProduct(l);
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point).scale(transparency(gp, lightSource, l, n));
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint geoPoint = scene.geometries.findClosestIntersection(ray);
        return geoPoint == null ? scene.background : calcColor(geoPoint, ray);
    }

    /**
     * Checks if a point is unshaded by finding any intersections between the point and the light source.
     *
     * @param gp The GeoPoint representing the point to check.
     * @param l  The Vector representing the direction from the point to the light source.
     * @param n  The Vector representing the normal at the point.
     * @param nl The dot product between the normal vector and the direction vector from the point to the light source.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    @SuppressWarnings("unused")
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
        // Compute the direction from the point to the light source
        Vector lightDirection = l.scale(-1);
        // Create  ray from  offset point to  light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Find  intersections between  ray and  geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        // Check if the intersections list is empty
        return intersections == null;
    }

    /**
     * This function calculate the reflected ray by several given parameters.
     *
     * @param gp A given GeoPoint to calculate the reflected ray on its point.
     * @param v  A given vector to calculate the reflected vector with it.
     * @param n  The normal to the geometry.
     * @return The reflected vector.
     */
    Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n, double nv) {
        Vector r = v.subtract(n.scale(2 * (nv)));
        return new Ray(gp.point, r, n);
    }

    /***
     * This function calculate the refracted ray by several given parameters.
     * @param gp A given GeoPoint to calculate the refracted ray on its point.
     * @param v A given vector to calculate the refracted vector with it.
     * @param n The normal to the geometry.
     * @return The refracted vector.
     */
    Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n, double nv) {
        Material material = gp.geometry.getMaterial();
        // Without snell low:
        if (snellParameter == material.snellParameter)
            return new Ray(gp.point, v, n);

        // With snell low:
        double Theta1 = Math.acos(-nv);
        double SinTheta2 = Math.sin(Theta1) * snellParameter / material.snellParameter;
        // There's a full reflected.
        if (SinTheta2 * SinTheta2 > 1 || isZero(SinTheta2 * SinTheta2 - 1)) {
            snellParameter = material.snellParameter;
            return constructReflectedRay(gp, v, n, nv);
        }
        double Theta2 = Math.asin(SinTheta2);
        Vector nvn = n.crossProduct(v).crossProduct(n).normalize();
        Vector r = n.scale(-Math.cos(Theta2)).add(nvn.scale(SinTheta2));
        snellParameter = material.snellParameter;
        return new Ray(gp.point, r, n);
    }

    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param v     the direction of the ray that caused the intersection
     * @param n     the normal to the geometry surface at the intersection point
     * @param nv    dot-product of (n,v)
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, Vector n, double nv, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n, nv), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp, v, n, nv), level, k, material.kT));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /***
     * This function calculate the strength of the shadow in a certain point.
     * @param gp The point to calculate the strength of the shadow at.
     * @param ls The light source which we calculate the shadow for.
     * @param l A vector from the camera to the point.
     * @param n A normal vector to the geometry's surface at the point gp.
     * @return The shadow's strength.
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
        Ray ray = new Ray(gp.point, l.scale(-1), n);
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray, ls.getDistance(gp.point));
        if (list == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        for (GeoPoint GP1 : list) {
            ktr = ktr.product(GP1.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }

    Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl < 0 ? -nl : nl);
    }

    Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = alignZero(v.dotProduct(r));
        return vr >= 0 ? Double3.ZERO : material.kS.scale(Math.pow(-vr, material.nShininess));
    }
}