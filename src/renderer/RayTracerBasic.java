package renderer;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of a ray tracer class.
 */

public class RayTracerBasic extends RayTraceBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = new Double3(1,1,1);
    private static final double MIN_CALC_COLOR_K = 0.001;
    private double SnellParameter = 1;

    private static final double DELTA = 0.1;
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
     * @param ray a ray
     * @return the color at this point
     */
    /* returns the color at a certain point
     *
     * @param intersection with the geometry
     * @param ray   the ray from the viewer
     * @return Color of the point
     */

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Geometry geometry = geoPoint.geometry;
        Color color = geometry.getEmission().add(calcLocalEffects(geoPoint, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());

    }



    /**
     * calculated light contribution from all light sources
     *
     * @param gp the geo point we calculate the color of
     * @param ray      ray from the camera to the point
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray)
    {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = n.dotProduct(v);
        if (isZero(nv))
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = n.dotProduct(l);
            if (nl * nv > 0) // sign(nl) == sing(nv)
            {
                Color iL = lightSource.getIntensity(gp.point).scale(transparency(gp, lightSource, l, n));
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * Calculate the Specular component of the light at this point
     *
     * @param ks             specular component
     * @param l              direction from light to point
     * @param n              normal from the object at the point
     * @param nl             dot-product n*l
     * @param v              direction from the camera to the point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity
     * @return the Specular component at the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double vr = alignZero(v.dotProduct(r));
        return vr >= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(-vr, nShininess)));
    }

    /**
     * Calculate the diffusive component of the light at this point
     *
     * @param kd             diffusive component
     * @param nl             dot-product n*l
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(nl > 0 ? nl : -nl));
    }

    @Override
    public Color traceRay(Ray ray)
    {
        GeoPoint geoPoint = scene.geometries.findClosestIntersection(ray);

        if (geoPoint == null)
            return scene.background;

        return calcColor(geoPoint, ray);
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
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
        // Compute the direction from the point to the light source
        Vector lightDirection = l.scale(-1);

        // Create  ray from  offset point to  light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        //get the distance
        double maxdistance = light.getDistance(gp.point);
        // Find  intersections between  ray and  geometries

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxdistance);

        // Check if the intersections list is empty
        if (intersections == null || intersections.isEmpty()) {
            return false;
        }

        return true;
    }
    /**
     * construct the refracted ray of the point on the geometry
     *
     * @param n     normal vector
     * @param point on the geometry
     * @return new ray
     */
    /**
     * This function calculate the reflected ray by several given parameters.
     * @param gp A given GeoPoint to calculate the reflected ray on its point.
     * @param v A given vector to calculate the reflected vector with it.
     * @param n The normal to the geometry.
     * @return The reflected vector.
     */
    Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n)
    {
        Vector r = v.subtract(n.scale( 2 * (v.dotProduct(n))));
        return new Ray(gp.point, r, n);
    }

    /***
     * This function calculate the refracted ray by several given parameters.
     * @param gp A given GeoPoint to calculate the refracted ray on its point.
     * @param v A given vector to calculate the refracted vector with it.
     * @param n The normal to the geometry.
     * @return The refracted vector.
     */
    Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n)
    {
        // Without snell low:
        if (SnellParameter == gp.geometry.getMaterial().SnellParameter)
            return new Ray(gp.point, v, n);

        // With snell low:
        double Theta1 = Math.acos(- v.dotProduct(n));
        double Sin_Theta2 = Math.sin(Theta1) * SnellParameter / gp.geometry.getMaterial().SnellParameter;
        if (Sin_Theta2*Sin_Theta2 > 1 || isZero(Sin_Theta2*Sin_Theta2 - 1)) // There's a full reflected.
        {
            SnellParameter = gp.geometry.getMaterial().SnellParameter;
            return constructReflectedRay(gp, v, n);
        }
        double Theta2 = Math.asin(Sin_Theta2);
        Vector nvn = n.crossProduct(v).crossProduct(n).normalize();
        Vector r = n.scale(-Math.cos(Theta2)).add(nvn.scale(Sin_Theta2));
        SnellParameter = gp.geometry.getMaterial().SnellParameter;
        return new Ray(gp.point, r, n);
    }

    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that caused the intersection
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n),level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp, v, n),level, k, material.kT));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx)
    {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        if (gp == null)
            return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
                : calcColor(gp, ray, level-1, kkx).scale(kx);
    }


    /***
     * This function calculate the strength of the shadow in a certain point.
     * @param gp The point to calculate the strength of the shadow at.
     * @param LS The light source which we calculate the shadow for.
     * @param l A vector from the camera to the point.
     * @param n A normal vector to the geometry's surface at the point gp.
     * @return The shadow's strength.
     */
    private Double3 transparency(GeoPoint gp, LightSource LS, Vector l, Vector n)
    {
        Ray ray = new Ray(gp.point,l.scale(-1), n);
        Double3 ktr = new Double3(1,1,1);
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray, LS.getDistance(gp.point));
        if (list == null)
            return ktr;

        for (GeoPoint GP1 : list)
        {
            ktr = ktr.product(GP1.geometry.getMaterial().kT);
            if (ktr.equals(Double3.ZERO))
                return Double3.ZERO;
        }
        return ktr;
    }
    Double3 calcDiffusive(Material material, double nl)
    {
        return material.kD.scale(Math.abs(nl));
    }
    Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v)
    {
        Vector r = l.subtract(n.scale( 2 * (l.dotProduct(n))));
        if (v.dotProduct(r) >= 0)
            return Double3.ZERO;
        return material.kS.scale(Math.pow(-v.dotProduct(r), material.nShininess));
    }
}